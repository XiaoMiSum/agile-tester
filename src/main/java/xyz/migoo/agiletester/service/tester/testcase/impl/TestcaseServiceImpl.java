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

package xyz.migoo.agiletester.service.tester.testcase.impl;

import org.springframework.stereotype.Service;
import xyz.migoo.agiletester.controller.tester.testcase.vo.TestcaseVO;
import xyz.migoo.agiletester.convert.testcase.TestcaseConvert;
import xyz.migoo.agiletester.dal.mysql.testcase.TestcaseMapper;
import xyz.migoo.agiletester.dal.objectdata.testcase.TestcaseDO;
import xyz.migoo.agiletester.service.tester.testcase.TestcaseService;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;

import javax.annotation.Resource;
import java.util.Objects;

import static xyz.migoo.agiletester.enums.ErrorCodeConstants.TESTCASE_IS_EXISTS;
import static xyz.migoo.agiletester.enums.ErrorCodeConstants.TESTCASE_NOT_EXISTS;

/**
 * @author xiaomi
 * Created on 2021/11/27 22:30
 */
@Service
public class TestcaseServiceImpl implements TestcaseService {

    @Resource
    private TestcaseMapper caseMapper;

    @Override
    public TestcaseVO getTestcase(String suiteId) {
        TestcaseDO testcase = caseMapper.selectBySuite(suiteId);
        return TestcaseConvert.INSTANCE.convert(testcase);
    }

    @Override
    public void addTestcase(TestcaseVO testcaseVO) {
        this.verify(testcaseVO.getSuiteId());
        TestcaseDO testcase = TestcaseConvert.INSTANCE.convert(testcaseVO);
        caseMapper.insert(testcase);
    }

    @Override
    public void updateTestcase(TestcaseVO testcaseVO) {
        // TODO: 2021/11/28 未来要解决多人协作时的相互覆盖问题
        this.verifyNotNull(testcaseVO.getSuiteId());
        caseMapper.updateById(TestcaseConvert.INSTANCE.convert(testcaseVO));
    }

    private void verify(String suiteId) {
        if (Objects.nonNull(caseMapper.selectBySuite(suiteId))) {
            throw ServiceExceptionUtil.get(TESTCASE_IS_EXISTS);
        }
    }

    private void verifyNotNull(String suiteId) {
        if (Objects.isNull(caseMapper.selectBySuite(suiteId))) {
            throw ServiceExceptionUtil.get(TESTCASE_NOT_EXISTS);
        }
    }
}
