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

package xyz.migoo.agiletester.dal.mysql.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.agiletester.controller.admin.user.vo.UserPageReqVO;
import xyz.migoo.agiletester.dal.objectdata.user.UserDO;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.QueryWrapperX;

import java.io.Serializable;

/**
 * @author xiaomi
 * Created on 2021/11/23 20:15
 */
@Mapper
public interface UserMapper extends BaseMapperX<UserDO> {

    /**
     * 通过登录名称查找用户信息
     *
     * @param username 登录名称
     * @return 用户表信息
     */
    default UserDO selectByUsername(String username) {
        return selectOne(new QueryWrapper<UserDO>().eq("login_name", username));
    }

    /**
     * 通过用户编号删除用户
     *
     * @param userId 用户编号
     * @return 用户表信息
     */
    @Override
    default int deleteById(Serializable userId) {
        return deleteById(new UserDO().setId((Long) userId));
    }

    /**
     * 分页查询
     *
     * @param reqVO 分页查询信息
     * @return 分页结果信息
     */
    default PageResult<UserDO> selectPage(UserPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<UserDO>()
                .likeIfPresent("name", reqVO.getName())
                .likeIfPresent("phone", reqVO.getPhone())
                .eqIfPresent("enabled", reqVO.getEnabled())
                .betweenIfPresent("create_time", reqVO.getBeginTime(), reqVO.getEndTime())
                .eqIfPresent("team_id", reqVO.getTeamId()));
    }

}
