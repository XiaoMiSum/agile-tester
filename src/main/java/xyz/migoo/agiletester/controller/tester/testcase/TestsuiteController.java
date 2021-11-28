/*
 *
 *  * The MIT License (MIT)
 *  *
 *  * Copyright (c) 2021. Lorem XiaoMi (mi_xiao@qq.com)
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining
 *  * a copy of this software and associated documentation files (the
 *  * 'Software'), to deal in the Software without restriction, including
 *  * without limitation the rights to use, copy, modify, merge, publish,
 *  * distribute, sublicense, and/or sell copies of the Software, and to
 *  * permit persons to whom the Software is furnished to do so, subject to
 *  * the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be
 *  * included in all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
 *  * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *  * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 *  * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 *  * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 */

package xyz.migoo.agiletester.controller.tester.testcase;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.agiletester.controller.tester.testcase.vo.TestsuiteVO;
import xyz.migoo.agiletester.service.tester.testcase.TestsuiteService;
import xyz.migoo.framework.common.pojo.Result;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author xiaomi
 * Created on 2021/11/27 13:25
 */
@Validated
@RestController
@RequestMapping("/testsuite")
public class TestsuiteController {

    @Resource
    private TestsuiteService suitesService;

    @GetMapping
    public Result<?> getTestsuite(@Valid @NotNull(message = "所属产品不能为空") Long productId) {
        return Result.getSuccessful(suitesService.getTestsuite(productId));
    }

    @PostMapping
    public Result<?> updateTestsuite(@Valid @RequestBody TestsuiteVO testsuiteVO) {
        suitesService.updateTestsuite(testsuiteVO);
        return Result.getSuccessful();
    }
}
