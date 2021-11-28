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

package xyz.migoo.agiletester.service.login;

import xyz.migoo.agiletester.controller.login.vo.AuthLoginReqVO;
import xyz.migoo.agiletester.controller.admin.user.vo.UserUpdateReqVO;
import xyz.migoo.framework.security.core.service.SecurityAuthFrameworkService;

import javax.validation.Valid;

/**
 * @author xiaomi
 * Created on 2021/11/21 14:47
 */
public interface LoginAuthService extends SecurityAuthFrameworkService {


    /**
     * 账号登录
     *
     * @param reqVO     登录信息
     * @param userIp    用户 IP
     * @param userAgent 用户 UA
     * @return 身份令牌，使用 JWT 方式
     */
    String signIn(@Valid AuthLoginReqVO reqVO, String userIp, String userAgent);


    /**
     * 注册第一步
     *
     * @param email 用户注册邮箱
     * @return token
     */
    String signupCheck(String email, String userIp, String userAgent);

    /**
     * 刷新token缓存的登录用户信息
     *
     * @param reqVO 用户注册信息
     */
    void refreshLoginUser(String token, UserUpdateReqVO reqVO);
}
