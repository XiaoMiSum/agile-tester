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

package xyz.migoo.agiletester.service.user;

import xyz.migoo.agiletester.controller.user.vo.*;
import xyz.migoo.agiletester.dal.objectdata.user.UserDO;
import xyz.migoo.framework.common.pojo.PageResult;

import java.util.List;

/**
 * @author xiaomi
 * Created on 2021/11/21 15:09
 */
public interface UserService {

    /**
     * 通过登录名称获取用户信息
     *
     * @param loginName 登录名称
     * @return 用户信息
     */
    UserDO getUserByLoginName(String loginName);

    /**
     * 通过用户编号获取用户信息
     *
     * @param id 用户编号
     * @return 用户信息
     */
    UserDO getUserById(Long id);

    /**
     * 创建用户
     *
     * @param reqVO 用户创建信息
     */
    void createUser(UserCreateReqVO reqVO);

    Long createUser(String loginName, String email);

    /**
     * 更新用户
     *
     * @param reqVO 用户更新信息
     */
    void updateUser(UserUpdateReqVO reqVO);

    /**
     * 删除用户
     *
     * @param userId 用户id
     */
    void deleteUser(Long userId);

    /**
     * 更新用户状态
     *
     * @param reqVO 用户更新信息
     */
    void updateStatus(UserUpdateStatusReqVO reqVO);

    /**
     * 更新用户密码
     *
     * @param reqVO 用户更新信息
     */
    void updatePassword(UserUpdatePasswordReqVO reqVO);

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    List<UserDO> getUsers();

    /**
     * 获取用户列表
     *
     * @param reqVO 查询信息
     * @return 用户列表
     */
    PageResult<UserDO> getPageUsers(UserPageReqVO reqVO);
}