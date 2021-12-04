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

package xyz.migoo.agiletester.convert.login;

import cn.hutool.json.JSONObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import xyz.migoo.agiletester.dal.objectdata.user.UserDO;
import xyz.migoo.framework.security.core.LoginUser;

/**
 * @author xiaomi
 * Created on 2021/11/21 15:13
 */
@Mapper
public interface AuthLoginConvert {


    AuthLoginConvert INSTANCE = Mappers.getMapper(AuthLoginConvert.class);

    /**
     * UserDO 转换为 LoginUser
     *
     * @param bean 用户表信息
     * @return 登录用户信息
     */
    @Mapping(source = "updateTime", target = "updateTime", ignore = true)
    LoginUser convert0(UserDO bean);

    /**
     * UserDO 转换为 LoginUser
     *
     * @param bean 用户表信息
     * @return 登录用户信息
     */
    default LoginUser convert(UserDO bean) {
        JSONObject extra = new JSONObject()
                .putOpt("name", bean.getName())
                .putOpt("avatar", bean.getAvatar())
                .putOpt("memo", bean.getMemo())
                .putOpt("email", bean.getEmail())
                .putOpt("phone", bean.getPhone())
                .putOpt("lastActivity", bean.getLastActivity())
                .putOpt("teamId", bean.getTeamId());
        return convert0(bean).setUsername(bean.getLoginName()).setExtra(extra);
    }
}
