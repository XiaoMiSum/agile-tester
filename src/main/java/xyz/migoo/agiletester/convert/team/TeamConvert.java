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

package xyz.migoo.agiletester.convert.team;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.agiletester.controller.admin.team.vo.TeamCreateReqVO;
import xyz.migoo.agiletester.controller.admin.team.vo.TeamRespVO;
import xyz.migoo.agiletester.controller.admin.team.vo.TeamUpdateReqVO;
import xyz.migoo.agiletester.dal.objectdata.team.TeamDO;

import java.util.List;
import java.util.Objects;

/**
 * @author xiaomi
 * Created on 2021/11/21 15:13
 */
@Mapper
public interface TeamConvert {

    TeamConvert INSTANCE = Mappers.getMapper(TeamConvert.class);

    /**
     * TeamCreateReqVO 转换为 TeamDO
     *
     * @param reqVO 团队创建信息
     * @return 团队表信息
     */
    TeamDO convert0(TeamCreateReqVO reqVO);

    /**
     * TeamCreateReqVO 转换为 TeamDO
     *
     * @param reqVO 团队创建信息
     * @return 团队表信息
     */
    default TeamDO convert(TeamCreateReqVO reqVO) {
        TeamDO team = convert0(reqVO);
        return team.setPid(Objects.isNull(team.getPid()) ? 0 : team.getPid());
    }

    /**
     * TeamUpdateReqVO 转换为 TeamDO
     *
     * @param reqVO 团队更新信息
     * @return 团队表信息
     */
    TeamDO convert(TeamUpdateReqVO reqVO);


    /**
     * TeamDO 转换为 TeamRespVO
     *
     * @param teams 团队表信息
     * @return 团队展示信息
     */
    List<TeamRespVO> convert(List<TeamDO> teams);
}
