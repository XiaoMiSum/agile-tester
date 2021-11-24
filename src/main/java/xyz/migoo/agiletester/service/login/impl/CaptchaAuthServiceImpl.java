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

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import xyz.migoo.framework.captcha.config.CaptchaProperties;
import xyz.migoo.framework.captcha.core.CaptchaAuthService;
import xyz.migoo.framework.captcha.core.CaptchaImage;

import javax.annotation.Resource;

import static xyz.migoo.agiletester.dal.redis.RedisKeyCoreConstants.CAPTCHA_CODE;

/**
 * @author xiaomi
 * Created on 2021/11/21 16:57
 */
@Slf4j
@Service
public class CaptchaAuthServiceImpl implements CaptchaAuthService {
    @Resource
    private CaptchaProperties captchaProperties;
    @Resource
    private StringRedisTemplate redis;

    @Override
    public CaptchaImage getCaptchaImage() {
        // 生成验证码
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
        // 缓存到 Redis 中
        String uuid = IdUtil.fastSimpleUUID();
        redis.opsForValue().set(formatKey(uuid), captcha.getCode(), captchaProperties.getTimeout());
        // 返回
        return new CaptchaImage(uuid, captcha.getImageBase64());
    }

    @Override
    public String getCaptchaCode(String uuid) {
        return redis.opsForValue().get(formatKey(uuid));
    }

    @Override
    public void deleteCaptchaCode(String uuid) {
        redis.delete(formatKey(uuid));
    }

    private static String formatKey(String uuid) {
        return String.format(CAPTCHA_CODE.getKeyTemplate(), uuid);
    }
}
