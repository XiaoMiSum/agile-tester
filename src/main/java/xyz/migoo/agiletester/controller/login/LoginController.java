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

package xyz.migoo.agiletester.controller.login;

import org.springframework.web.bind.annotation.*;
import xyz.migoo.agiletester.controller.login.vo.AuthLoginReqVO;
import xyz.migoo.agiletester.controller.login.vo.AuthLoginRspVO;
import xyz.migoo.agiletester.controller.admin.user.vo.UserUpdateReqVO;
import xyz.migoo.agiletester.service.login.LoginAuthService;
import xyz.migoo.agiletester.service.admin.user.UserService;
import xyz.migoo.framework.captcha.core.CaptchaAuthService;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.common.util.servlet.ServletUtils;
import xyz.migoo.framework.common.validation.Email;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotations.CurrentUser;
import xyz.migoo.framework.security.core.annotations.Token;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @author xiaomi
 * Created on 2021/11/21 14:46
 */
@RestController
@RequestMapping
public class LoginController {

    @Resource
    private LoginAuthService loginAuthService;
    @Resource
    private CaptchaAuthService captchaAuthService;
    @Resource
    private UserService userService;

    /**
     * ????????????
     *
     * @param reqVO ????????????
     * @return token
     */
    @PostMapping("/sign-in")
    public Result<AuthLoginRspVO> signIn(@RequestBody @Valid AuthLoginReqVO reqVO) {
        String token = loginAuthService.signIn(reqVO, ServletUtils.getClientIP(), ServletUtils.getUserAgent());
        return Result.getSuccessful(AuthLoginRspVO.builder().token(token).build());
    }

    /**
     * ????????????  -1 ????????????????????????????????????????????????????????????????????????
     *
     * @param email ????????????????????????
     * @return ???????????? ??? ????????????
     */
    @PutMapping("/signup-check")
    public Result<?> signupCheck(@NotEmpty(message = "????????????????????????") @Email() String email) {
        String token = loginAuthService.signupCheck(email, ServletUtils.getClientIP(), ServletUtils.getUserAgent());
        return Result.getSuccessful(AuthLoginRspVO.builder().token(token).build());
    }

    /**
     * ???????????? -2 ???????????????????????? ??????token?????????????????????
     *
     * @param reqVO ????????????
     * @return ???????????? ??? ????????????
     */
    @PostMapping("/signup")
    public Result<?> signup(@Token String token, @CurrentUser LoginUser loginUser, @RequestBody @Valid UserUpdateReqVO reqVO) {
        // ?????????
        userService.updateUser(reqVO.setId(loginUser.getId()));
        loginAuthService.refreshLoginUser(token, reqVO);
        return Result.getSuccessful();
    }

    /**
     * ?????????????????????????????????????????????
     */
    @PostMapping("/sign-out")
    public Result<?> signOut() {
        return Result.getSuccessful();
    }

    /**
     * ???????????????
     *
     * @return ???????????????
     */
    @GetMapping("/captcha")
    public Result<?> getCaptcha() {
        return Result.getSuccessful(captchaAuthService.getCaptchaImage());
    }

    /**
     * ??????????????????????????????
     *
     * @return ??????????????????
     */
    @GetMapping("/user-info")
    public Result<?> getCaptcha(@CurrentUser LoginUser user) {
        return Result.getSuccessful(user);
    }
}
