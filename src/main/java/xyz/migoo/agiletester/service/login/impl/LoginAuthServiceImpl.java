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

import cn.hutool.json.JSONObject;
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
import static xyz.migoo.framework.common.enums.NumberConstants.N_3;

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
        // ?????? username ????????? SysUserDO
        UserDO user = userService.getUserByLoginName(username);
        if (user == null ) {
            throw new UsernameNotFoundException(username);
        }
        if (!user.getEnabled()) {
            throw ServiceExceptionUtil.get(USER_IS_DISABLED);
        }
        // ?????? LoginUser ??????
        return AuthLoginConvert.INSTANCE.convert(user);
    }

    @Override
    public String signIn(AuthLoginReqVO reqVO, String userIp, String userAgent) {
        // ???????????????????????????
        this.verifyCaptcha(reqVO.getUuid(), reqVO.getCode());
        // ????????????????????????????????????
        LoginUser loginUser = this.login0(reqVO.getUsername(), reqVO.getPassword());
        // ????????????????????? Redis ???????????? sessionId ??????
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
        LoginUser loginUser = new LoginUser()
                .setId(userId)
                .setLoginName(username)
                .setExtra(new JSONObject().putOpt("email", email));
        return userSessionAuthService.createUserSession(loginUser, userIp, userAgent);
    }

    @Override
    public void refreshLoginUser(String token, UserUpdateReqVO reqVO) {
        LoginUser loginUser = userSessionAuthService.getLoginUser(token);
        loginUser.getExtra()
                .putOpt("name", reqVO.getName())
                .putOpt("phone", reqVO.getPhone())
                .putOpt("teamId", reqVO.getTeamId());
        userSessionAuthService.refreshUserSession(token, loginUser);
    }

    @Override
    public LoginUser verifyTokenAndRefresh(String token) {
        // ?????? LoginUser
        LoginUser loginUser = userSessionAuthService.getLoginUser(token);
        if (Objects.nonNull(loginUser)) {
            // ?????? LoginUser ??????
            this.refreshLoginUserCache(token, loginUser);
        }
        return loginUser;
    }

    @Override
    public void signOut(String token) {
        if (Objects.nonNull(userSessionAuthService.getLoginUser(token))) {
            // ?????? session
            userSessionAuthService.deleteUserSession(token);
        }
    }

    private LoginUser login0(String username, String password) {
        // ????????????
        Authentication authentication;
        try {
            // ?????? Spring Security ??? AuthenticationManager#authenticate(...) ???????????????????????????????????????
            // ??????????????????????????? loadUserByUsername ??????????????? User ??????
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException badCredentialsException) {
            throw ServiceExceptionUtil.get(AUTH_LOGIN_BAD_CREDENTIALS);
        } catch (DisabledException disabledException) {
            throw ServiceExceptionUtil.get(AUTH_LOGIN_USER_DISABLED);
        } catch (AuthenticationException authenticationException) {
            log.error("[login0][username({}) ??????????????????]", username, authenticationException);
            throw ServiceExceptionUtil.get(AUTH_LOGIN_FAIL_UNKNOWN);
        }
        // ?????????????????????
        Assert.notNull(authentication.getPrincipal(), "Principal ????????????");
        return (LoginUser) authentication.getPrincipal();
    }


    private void verifyCaptcha(String captchaUuid, String captchaCode) {
        String code = captchaAuthService.getCaptchaCode(captchaUuid);
        // ?????????????????? ??? ?????????
        if (code == null || !code.equals(captchaCode)) {
            throw ServiceExceptionUtil.get(AUTH_LOGIN_CAPTCHA_CODE_ERROR);
        }
        // ????????????????????????????????????
        captchaAuthService.deleteCaptchaCode(captchaUuid);
    }

    private void refreshLoginUserCache(String token, LoginUser loginUser) {
        // ??? 1/3 ??? Session ????????????????????? LoginUser ??????
        if (System.currentTimeMillis() - loginUser.getUpdateTime().getTime() > userSessionAuthService.getSessionTimeoutMillis() / N_3) {
            // ???????????? SysUserDO ??????
            UserDO user = userService.getUserById(loginUser.getId());
            if (user == null || !user.getEnabled()) {
                // ?????? token ????????????????????????????????????????????? token ??????????????????????????????????????????
                throw ServiceExceptionUtil.get(AUTH_TOKEN_EXPIRED);
            }
            // ?????? LoginUser ??????
            userSessionAuthService.refreshUserSession(token, loginUser);
        }
    }
}
