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

package xyz.migoo.agiletester.enums;

import xyz.migoo.framework.common.exception.ErrorCode;

/**
 * 错误码枚举类
 *
 * @author xiaomi
 * Created on 2021/11/21 15:25
 */
public interface ErrorCodeConstants {

    // ========== AUTH 模块 1002000000 ==========

    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1002000000, "登录失败，用户名或密码不正确");
    ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1002000001, "登录失败，用户已停用");
    ErrorCode AUTH_LOGIN_FAIL_UNKNOWN = new ErrorCode(1002000002, "登录失败");
    ErrorCode AUTH_LOGIN_CAPTCHA_CODE_ERROR = new ErrorCode(1002000003, "验证码不正确");
    ErrorCode AUTH_TOKEN_EXPIRED = new ErrorCode(1002000004, "Token 已经过期");
    ErrorCode AUTH_THIRD_LOGIN_NOT_BIND = new ErrorCode(1002000005, "未绑定账号，需要进行绑定");

    // ========== USER 模块 1003000000 ==========

    ErrorCode USER_IS_EXISTS = new ErrorCode(1003000000, "注册失败，邮箱地址已存在");
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1003000001, "操作失败，操作用户不存在");
    ErrorCode USER_PASSWORD_CONFIRM_UNCONFORMITY = new ErrorCode(1003000002, "操作失败，新密码与确认密码不一致");
    ErrorCode USER_ORIGINAL_PASSWORD_UNCONFORMITY = new ErrorCode(1003000003, "操作失败，原密码错误");

    // ========== TEAM 模块 1004000000 ==========

    ErrorCode TEAM_IS_EXISTS = new ErrorCode(1004000000, "操作失败，团队名称已存在");
    ErrorCode TEAM_NOT_EXISTS = new ErrorCode(1004000001, "操作失败，操作团队不存在");

    // ========== PRODUCT 模块 1005000000 ==========

    ErrorCode PRODUCT_IS_EXISTS = new ErrorCode(1005000000, "操作失败，产品名称已存在");
    ErrorCode PRODUCT_NOT_EXISTS = new ErrorCode(1005000001, "操作失败，操作产品不存在");
}
