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

package xyz.migoo.agiletester.controller.team;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.agiletester.controller.team.vo.TeamCreateReqVO;
import xyz.migoo.agiletester.controller.team.vo.TeamUpdateReqVO;
import xyz.migoo.agiletester.convert.team.TeamConvert;
import xyz.migoo.agiletester.service.team.TeamService;
import xyz.migoo.framework.common.pojo.Result;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author xiaomi
 * Created on 2021/11/21 17:36
 */
@RequestMapping("/team")
@RestController
@Validated
public class TeamController {

    @Resource
    private TeamService teamService;

    /**
     * 创建团队接口
     *
     * @param reqVO 团队信息
     * @return 结果信息
     */
    @PutMapping
    public Result<?> createTeam(@RequestBody @Valid TeamCreateReqVO reqVO) {
        teamService.createTeam(reqVO);
        return Result.getSuccessful();
    }

    /**
     * 创建团队接口
     *
     * @param reqVO 团队信息
     * @return 结果信息
     */
    @PostMapping
    public Result<?> updateTeam(@RequestBody @Valid TeamUpdateReqVO reqVO) {
        teamService.updateTeam(reqVO);
        return Result.getSuccessful();
    }

    /**
     * 删除团队接口
     *
     * @param id 团队编号
     * @return 结果信息
     */
    @DeleteMapping
    public Result<?> deleteTeam(Long id) {
        teamService.deleteTeam(id);
        return Result.getSuccessful();
    }

    /**
     * 获取团队列表接口
     *
     * @return 结果信息
     */
    @GetMapping
    public Result<?> getTeamList() {
        return Result.getSuccessful(TeamConvert.INSTANCE.convert(teamService.getTeamList()));
    }
}
