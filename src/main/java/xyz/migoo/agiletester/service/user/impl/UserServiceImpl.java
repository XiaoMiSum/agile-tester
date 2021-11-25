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

package xyz.migoo.agiletester.service.user.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.migoo.agiletester.controller.user.vo.*;
import xyz.migoo.agiletester.convert.user.UserConvert;
import xyz.migoo.agiletester.dal.mysql.user.UserMapper;
import xyz.migoo.agiletester.dal.objectdata.user.UserDO;
import xyz.migoo.agiletester.service.user.UserService;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static xyz.migoo.agiletester.enums.ErrorCodeConstants.*;

/**
 * @author xiaomi
 * Created on 2021/11/21 16:56
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDO getUserByLoginName(String loginName) {
        return userMapper.selectByUsername(loginName);
    }

    @Override
    public UserDO getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public void createUser(UserCreateReqVO reqVO) {
        UserDO user = UserConvert.INSTANCE.convert(reqVO);
        this.verify(user.getLoginName());
        String password = StrUtil.isBlank(user.getPassword()) ? RandomUtil.randomString(10) : user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userMapper.insert(user);
        // todo 发送邮件
    }

    @Override
    public Long createUser(String loginName, String email) {
        UserDO user = new UserDO()
                .setLoginName(loginName)
                .setEmail(email)
                // 密码不能为空，这边随机生成一个，后续更新为用户设置的
                .setPassword(passwordEncoder.encode(RandomUtil.randomString(10)));
        userMapper.insert(user);
        return user.getId();
    }

    @Override
    public void updateUser(UserUpdateReqVO reqVO) {
        // 校验正确性
        this.verify(reqVO.getId());
        // 更新用户
        UserDO user = UserConvert.INSTANCE.convert(reqVO);
        if (StrUtil.isNotBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userMapper.updateById(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userMapper.deleteById(userId);
    }

    @Override
    public void updateStatus(UserUpdateStatusReqVO reqVO) {
        // 校验正确性
        UserDO user = this.verify(reqVO.getId());
        // 更新用户
        user.setEnabled(reqVO.getEnabled());
        userMapper.updateById(user);
    }

    @Override
    public void updatePassword(UserUpdatePasswordReqVO reqVO) {
        if (!Objects.equals(reqVO.getPassword(), reqVO.getConfirm())) {
            throw ServiceExceptionUtil.get(USER_PASSWORD_CONFIRM_UNCONFORMITY);
        }
        // 校验正确性
        UserDO user = this.verify(reqVO.getId());
        if (!BCrypt.checkpw(reqVO.getOriginal(), user.getPassword())) {
            throw ServiceExceptionUtil.get(USER_ORIGINAL_PASSWORD_UNCONFORMITY);
        }
        user.setPassword(passwordEncoder.encode(reqVO.getPassword()));
        userMapper.updateById(user);
    }

    @Override
    public List<UserDO> getUsers() {
        return userMapper.selectList();
    }

    @Override
    public PageResult<UserDO> getPageUsers(UserPageReqVO reqVO) {
        return userMapper.selectPage(reqVO);
    }

    private void verify(String loginName) {
        if (this.getUserByLoginName(loginName) != null) {
            throw ServiceExceptionUtil.get(USER_IS_EXISTS);
        }
    }

    private UserDO verify(Long id) {
        UserDO user = this.getUserById(id);
        if (user == null) {
            throw ServiceExceptionUtil.get(USER_NOT_EXISTS);
        }
        return user;
    }
}
