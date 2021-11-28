/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021.  Lorem XiaoMi (mi_xiao@qq.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * 'Software'), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package xyz.migoo.agiletester.service.login.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.migoo.agiletester.controller.admin.user.vo.UserUpdateReqVO;
import xyz.migoo.agiletester.controller.login.vo.AuthLoginReqVO;
import xyz.migoo.agiletester.convert.login.AuthLoginConvert;
import xyz.migoo.agiletester.dal.objectdata.user.UserDO;
import xyz.migoo.agiletester.service.admin.user.UserService;
import xyz.migoo.agiletester.service.login.LoginAuthService;
import xyz.migoo.agiletester.service.login.UserSessionAuthService;
import xyz.migoo.framework.captcha.core.CaptchaAuthService;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.framework.security.core.LoginUser;

import javax.annotation.Resource;
import java.util.Objects;

import static xyz.migoo.agiletester.enums.ErrorCodeConstants.*;

/**
 * @author xiaomi
 * Created on 2021/11/21 15:01
 */
@Service
@Slf4j
public class LoginAuthServiceImpl implements LoginAuthService {

    @Resource
    @Lazy
    private AuthenticationManager authenticationManager;
    @Resource
    private UserService userService;
    @Resource
    private UserSessionAuthService userSessionAuthService;
    @Resource
    private CaptchaAuthService captchaAuthService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取 username 对应的 SysUserDO
        UserDO user = userService.getUserByLoginName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        // 创建 LoginUser 对象
        return AuthLoginConvert.INSTANCE.convert(user);
    }

    @Override
    public String signIn(AuthLoginReqVO reqVO, String userIp, String userAgent) {
        // 判断验证码是否正确
        this.verifyCaptcha(reqVO.getUuid(), reqVO.getCode());
        // 使用账号密码，进行登录。
        LoginUser loginUser = this.login0(reqVO.getUsername(), reqVO.getPassword());
        // 缓存登陆用户到 Redis 中，返回 sessionId 编号
        return userSessionAuthService.createUserSession(loginUser, userIp, userAgent);
    }

    @Override
    public String signupCheck(String email, String userIp, String userAgent) {
        String username = email.split("@")[0];
        UserDO user = userService.getUserByLoginName(username);
        if (user != null) {
            throw ServiceExceptionUtil.get(USER_IS_EXISTS);
        }
        Long userId = userService.createUser(username, email);
        LoginUser loginUser = new LoginUser().setId(userId).setEmail(email).setLoginName(username);
        return userSessionAuthService.createUserSession(loginUser, userIp, userAgent);
    }

    @Override
    public void refreshLoginUser(String token, UserUpdateReqVO reqVO) {
        LoginUser loginUser = userSessionAuthService.getLoginUser(token);
        loginUser.setName(reqVO.getName())
                .setPhone(reqVO.getPhone())
                .setTeamId(reqVO.getTeamId());
        userSessionAuthService.refreshUserSession(token, loginUser);
    }

    @Override
    public LoginUser verifyTokenAndRefresh(String token) {
        // 获得 LoginUser
        LoginUser loginUser = userSessionAuthService.getLoginUser(token);
        if (Objects.nonNull(loginUser)) {
            // 刷新 LoginUser 缓存
            this.refreshLoginUserCache(token, loginUser);
        }
        return loginUser;
    }

    @Override
    public void signOut(String token) {
        if (Objects.nonNull(userSessionAuthService.getLoginUser(token))) {
            // 删除 session
            userSessionAuthService.deleteUserSession(token);
        }
    }

    private LoginUser login0(String username, String password) {
        // 用户验证
        Authentication authentication;
        try {
            // 调用 Spring Security 的 AuthenticationManager#authenticate(...) 方法，使用账号密码进行认证
            // 在其内部，会调用到 loadUserByUsername 方法，获取 User 信息
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException badCredentialsException) {
            throw ServiceExceptionUtil.get(AUTH_LOGIN_BAD_CREDENTIALS);
        } catch (DisabledException disabledException) {
            throw ServiceExceptionUtil.get(AUTH_LOGIN_USER_DISABLED);
        } catch (AuthenticationException authenticationException) {
            log.error("[login0][username({}) 发生未知异常]", username, authenticationException);
            throw ServiceExceptionUtil.get(AUTH_LOGIN_FAIL_UNKNOWN);
        }
        // 登录成功的日志
        Assert.notNull(authentication.getPrincipal(), "Principal 不会为空");
        return (LoginUser) authentication.getPrincipal();
    }


    private void verifyCaptcha(String captchaUuid, String captchaCode) {
        String code = captchaAuthService.getCaptchaCode(captchaUuid);
        // 验证码不存在 或 不正确
        if (code == null || !code.equals(captchaCode)) {
            throw ServiceExceptionUtil.get(AUTH_LOGIN_CAPTCHA_CODE_ERROR);
        }
        // 正确，所以要删除下验证码
        captchaAuthService.deleteCaptchaCode(captchaUuid);
    }

    private void refreshLoginUserCache(String token, LoginUser loginUser) {
        // 每 1/3 的 Session 超时时间，刷新 LoginUser 缓存
        if (System.currentTimeMillis() - loginUser.getUpdateTime().getTime() > userSessionAuthService.getSessionTimeoutMillis() / 3) {
            // 重新加载 SysUserDO 信息
            UserDO user = userService.getUserById(loginUser.getId());
            if (user == null || !user.getEnabled()) {
                // 校验 token 时，用户被禁用的情况下，也认为 token 过期，方便前端跳转到登录界面
                throw ServiceExceptionUtil.get(AUTH_TOKEN_EXPIRED);
            }
            // 刷新 LoginUser 缓存
            userSessionAuthService.refreshUserSession(token, loginUser);
        }
    }
}
