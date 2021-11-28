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

package xyz.migoo.agiletester.controller.admin.user;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.agiletester.controller.admin.user.vo.*;
import xyz.migoo.agiletester.convert.user.UserConvert;
import xyz.migoo.agiletester.service.admin.user.UserService;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotations.CurrentUser;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author xiaomi
 * Created on 2021/11/21 17:36
 */
@RequestMapping("/user")
@RestController
@Validated
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 创建用户接口
     *
     * @param reqVO 用户创建信息
     * @return 创建结果
     */
    @PutMapping
    public Result<?> createUser(@RequestBody @Valid UserCreateReqVO reqVO) {
        userService.createUser(reqVO);
        return Result.getSuccessful();
    }

    /**
     * 更新用户接口
     *
     * @param reqVO 用户更新信息
     * @return 更新结果
     */
    @PostMapping
    public Result<?> updateUser(@RequestBody @Valid UserUpdateReqVO reqVO) {
        userService.updateUser(reqVO);
        return Result.getSuccessful();
    }

    /**
     * 删除用户接口
     *
     * @param id 用户编号
     * @return 删除结果
     */
    @DeleteMapping
    public Result<?> deleteUser(@NotNull(message = "用户编号不能为空") Long id) {
        userService.deleteUser(id);
        return Result.getSuccessful();
    }

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    @GetMapping
    public Result<PageResult<?>> getPageUsers(UserPageReqVO reqVO) {
        return Result.getSuccessful(UserConvert.INSTANCE.convert(userService.getPageUsers(reqVO)));
    }

    /**
     * 更新用户状态接口
     *
     * @param reqVO 用户更新信息
     * @return 更新结果
     */
    @PostMapping("/status")
    public Result<?> updateStatus(@RequestBody @Valid UserUpdateStatusReqVO reqVO) {
        userService.updateStatus(reqVO);
        return Result.getSuccessful();
    }

    /**
     * 更新用户接口
     *
     * @param reqVO 用户更新信息
     * @return 更新结果
     */
    @PostMapping("/password")
    public Result<?> updatePassword(@CurrentUser LoginUser loginUser, @RequestBody @Valid UserUpdatePasswordReqVO reqVO) {
        userService.updatePassword(reqVO.setId(loginUser.getId()));
        return Result.getSuccessful();
    }

    /**
     * 重置用户密码接口
     *
     * @param userId 用户编号
     * @return 更新结果
     */
    @PostMapping("/{userId}")
    public Result<?> resetPassword(@NotNull(message = "用户编号不能为空") @PathVariable("userId") Long userId) {
        userService.resetPassword(userId);
        return Result.getSuccessful();
    }


}
