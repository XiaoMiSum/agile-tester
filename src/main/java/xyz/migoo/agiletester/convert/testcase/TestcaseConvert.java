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

package xyz.migoo.agiletester.convert.testcase;

import cn.hutool.json.JSONUtil;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.agiletester.controller.tester.testcase.vo.TestcaseVO;
import xyz.migoo.agiletester.dal.objectdata.testcase.TestcaseDO;

import java.util.Objects;

/**
 * @author xiaomi
 * Created on 2021/11/27 14:33
 */
@Mapper
public interface TestcaseConvert {

    TestcaseConvert INSTANCE = Mappers.getMapper(TestcaseConvert.class);

    /**
     * TestsuiteVO 转换为 TestsuiteDO
     *
     * @param testcaseVO 前端展示对象
     * @return 测试用例表对象
     */
    default TestcaseDO convert(TestcaseVO testcaseVO) {
        return new TestcaseDO().setId(testcaseVO.getId())
                .setType(testcaseVO.getType())
                .setContent(testcaseVO.getContent().toString())
                .setExtra(Objects.isNull(testcaseVO.getExtra()) ? "{}" : testcaseVO.getExtra().toString())
                .setMemo(testcaseVO.getMemo())
                .setSuiteId(testcaseVO.getSuiteId());
    }


    /**
     * TestsuiteDO 转换为 TestsuiteVO
     *
     * @param testcaseDO 测试用例表对象
     * @return 前端展示对象
     */
    default TestcaseVO convert(TestcaseDO testcaseDO) {
        return new TestcaseVO().setId(testcaseDO.getId())
                .setType(testcaseDO.getType())
                .setContent(JSONUtil.parseObj(testcaseDO.getContent()))
                .setExtra(JSONUtil.parseObj(testcaseDO.getExtra()))
                .setMemo(testcaseDO.getMemo())
                .setSuiteId(testcaseDO.getSuiteId());
    }
}
