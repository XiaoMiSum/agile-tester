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

package xyz.migoo.agiletester.convert.testsuite;

import com.fasterxml.jackson.core.type.TypeReference;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.agiletester.controller.tester.testcase.vo.TestsuiteNode;
import xyz.migoo.agiletester.controller.tester.testcase.vo.TestsuiteVO;
import xyz.migoo.agiletester.dal.objectdata.testsuite.TestsuiteDO;
import xyz.migoo.framework.common.util.json.JsonUtils;

import java.util.List;

/**
 * @author xiaomi
 * Created on 2021/11/27 14:33
 */
@Mapper
public interface TestsuiteConvert {

    TestsuiteConvert INSTANCE = Mappers.getMapper(TestsuiteConvert.class);

    /**
     * TestsuiteVO 转换为 TestsuiteDO
     *
     * @param testsuiteVO 前端展示对象
     * @return 测试用例集合表对象
     */
    default TestsuiteDO convert(TestsuiteVO testsuiteVO) {
        return new TestsuiteDO()
                .setContent(JsonUtils.toJsonString(testsuiteVO.getContent()))
                .setId(testsuiteVO.getId())
                .setProductId(testsuiteVO.getProductId());
    }

    /**
     * TestsuiteDO 转换为 TestsuiteVO
     *
     * @param testsuiteDO 测试用例集合表对象
     * @return 前端展示对象
     */
    default TestsuiteVO convert(TestsuiteDO testsuiteDO) {
        return new TestsuiteVO()
                .setContent(JsonUtils.parseObject(testsuiteDO.getContent(), new TypeReference<List<TestsuiteNode>>() {
                }))
                .setId(testsuiteDO.getId())
                .setProductId(testsuiteDO.getProductId());
    }
}
