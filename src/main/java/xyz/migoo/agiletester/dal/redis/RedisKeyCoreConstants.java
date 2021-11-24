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

package xyz.migoo.agiletester.dal.redis;

import xyz.migoo.framework.redis.core.RedisKeyDefine;
import xyz.migoo.framework.security.core.LoginUser;

import static xyz.migoo.framework.redis.core.RedisKeyDefine.KeyTypeEnum.STRING;

/**
 * @author xiaomi
 * Created on 2021/11/21 16:11
 */
public interface RedisKeyCoreConstants {

    /**
     * 登录用户缓存key模板，参数为 sessionId
     */
    RedisKeyDefine LOGIN_USER = new RedisKeyDefine("登录用户的缓存", "login_user:%s", STRING, LoginUser.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    /**
     * 验证码缓存key模板，参数为 uuid
     */
    RedisKeyDefine CAPTCHA_CODE = new RedisKeyDefine("验证码的缓存", "captcha_code:%s", STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);
}
