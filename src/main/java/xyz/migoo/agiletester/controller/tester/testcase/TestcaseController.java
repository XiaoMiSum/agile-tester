/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021. Lorem XiaoMi (mi_xiao@qq.com)
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

package xyz.migoo.agiletester.controller.tester.testcase;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.agiletester.controller.tester.testcase.vo.TestcaseVO;
import xyz.migoo.agiletester.service.tester.testcase.TestcaseService;
import xyz.migoo.framework.common.pojo.Result;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @author xiaomi
 * Created on 2021/11/27 12:47
 */
@Validated
@RestController
@RequestMapping("/testcase")
public class TestcaseController {

    @Resource
    private TestcaseService testcaseService;

    @GetMapping()
    public Result<?> getTestcase(@NotEmpty(message = "测试集合id不能为空") String suiteId) {
        return Result.getSuccessful(testcaseService.getTestcase(suiteId));
    }

    @PutMapping()
    public Result<?> addTestcase(@Valid @RequestBody TestcaseVO testcaseVO) {
        testcaseService.addTestcase(testcaseVO);
        return Result.getSuccessful();
    }

    @PostMapping()
    public Result<?> updateTestcase(@Valid @RequestBody TestcaseVO testcaseVO) {
        testcaseService.updateTestcase(testcaseVO);
        return Result.getSuccessful();
    }
}
