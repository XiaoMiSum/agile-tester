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

package xyz.migoo.agiletester.service.admin.team;

import xyz.migoo.agiletester.controller.admin.team.vo.TeamCreateReqVO;
import xyz.migoo.agiletester.controller.admin.team.vo.TeamUpdateReqVO;
import xyz.migoo.agiletester.dal.objectdata.team.TeamDO;

import java.util.List;

/**
 * @author xiaomi
 * Created on 2021/11/21 15:09
 */
public interface TeamService {

    /**
     * 创建团队
     *
     * @param reqVO 团队信息
     */
    void createTeam(TeamCreateReqVO reqVO);

    /**
     * 更新团队
     *
     * @param reqVO 团队信息
     */
    void updateTeam(TeamUpdateReqVO reqVO);

    /**
     * 删除团队
     *
     * @param id 团队编号
     */
    void deleteTeam(Long id);

    /**
     * 获取团队列表
     *
     * @param name 团队名称
     * @return 团队列表
     */
    List<TeamDO> getTeamList(String name);
}
