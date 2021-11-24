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

package xyz.migoo.agiletester.convert.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.agiletester.controller.user.vo.UserCreateReqVO;
import xyz.migoo.agiletester.controller.user.vo.UserRespVO;
import xyz.migoo.agiletester.controller.user.vo.UserUpdateReqVO;
import xyz.migoo.agiletester.dal.objectdata.user.UserDO;
import xyz.migoo.framework.common.pojo.PageResult;

/**
 * @author xiaomi
 * Created on 2021/11/21 15:13
 */
@Mapper
public interface UserConvert {


    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    /**
     * UserSignUpReqVO 转换为 UserDO
     *
     * @param reqVO 用户注册信息
     * @return 用户表信息
     */
    UserDO convert0(UserCreateReqVO reqVO);

    /**
     * UserSignUpReqVO 转换为 UserDO
     *
     * @param reqVO 用户注册信息
     * @return 用户表信息
     */
    default UserDO convert(UserCreateReqVO reqVO) {
        UserDO user = convert0(reqVO);
        return user.setLoginName(reqVO.getEmail().substring(0, reqVO.getEmail().indexOf("@")));
    }

    /**
     * UserUpdateReqVO 转换为 UserDO
     *
     * @param reqVO 用户更新信息
     * @return 用户表信息
     */
    UserDO convert(UserUpdateReqVO reqVO);

    /**
     * 分页用户信息转换
     *
     * @param user 用户表信息
     * @return 用户展示信息
     */
    PageResult<UserRespVO> convert(PageResult<UserDO> user);
}
