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

package xyz.migoo.agiletester.service.admin.team.impl;

import org.springframework.stereotype.Service;
import xyz.migoo.agiletester.controller.admin.team.vo.TeamCreateReqVO;
import xyz.migoo.agiletester.controller.admin.team.vo.TeamUpdateReqVO;
import xyz.migoo.agiletester.convert.team.TeamConvert;
import xyz.migoo.agiletester.dal.mysql.team.TeamMapper;
import xyz.migoo.agiletester.dal.objectdata.team.TeamDO;
import xyz.migoo.agiletester.service.admin.team.TeamService;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static xyz.migoo.agiletester.enums.ErrorCodeConstants.TEAM_IS_EXISTS;
import static xyz.migoo.agiletester.enums.ErrorCodeConstants.TEAM_NOT_EXISTS;

/**
 * @author xiaomi
 * Created on 2021/11/21 15:09
 */
@Service
public class TeamServiceImpl implements TeamService {

    @Resource
    private TeamMapper teamMapper;

    @Override
    public void createTeam(TeamCreateReqVO reqVO) {
        this.verify(reqVO.getName(), reqVO.getPid());
        TeamDO team = TeamConvert.INSTANCE.convert(reqVO);
        teamMapper.insert(team);
    }

    @Override
    public void updateTeam(TeamUpdateReqVO reqVO) {
        this.verify(reqVO.getId());
        TeamDO team = TeamConvert.INSTANCE.convert(reqVO);
        teamMapper.updateById(team);
    }

    @Override
    public void deleteTeam(Long id) {
        teamMapper.deleteById(id);
    }

    @Override
    public List<TeamDO> getTeamList(String name) {
        return teamMapper.selectList(name);
    }

    private void verify(String name, Long pid) {
        if (Objects.nonNull(teamMapper.selectByName(name, Objects.isNull(pid) ? 0 : pid))) {
            throw ServiceExceptionUtil.get(TEAM_IS_EXISTS);
        }
    }

    private void verify(Long id) {
        if (Objects.isNull(teamMapper.selectById(id))) {
            throw ServiceExceptionUtil.get(TEAM_NOT_EXISTS);
        }
    }
}
