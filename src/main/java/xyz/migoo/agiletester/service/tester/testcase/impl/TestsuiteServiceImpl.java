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

import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Service;
import xyz.migoo.agiletester.controller.tester.testcase.vo.TestsuiteNode;
import xyz.migoo.agiletester.controller.tester.testcase.vo.TestsuiteVO;
import xyz.migoo.agiletester.convert.testsuite.TestsuiteConvert;
import xyz.migoo.agiletester.dal.mysql.product.ProductMapper;
import xyz.migoo.agiletester.dal.mysql.testsuite.TestsuiteMapper;
import xyz.migoo.agiletester.dal.objectdata.product.ProductDO;
import xyz.migoo.agiletester.dal.objectdata.testsuite.TestsuiteDO;
import xyz.migoo.agiletester.service.tester.testcase.TestsuiteService;
import xyz.migoo.framework.common.util.json.JsonUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xiaomi
 * Created on 2021/11/27 14:17
 */
@Service
public class TestsuiteServiceImpl implements TestsuiteService {

    @Resource
    private TestsuiteMapper suiteMapper;
    @Resource
    private ProductMapper productMapper;

    @Override
    public TestsuiteVO getTestsuite(Long productId) {
        TestsuiteDO suite = suiteMapper.selectByProduct(productId);
        if (Objects.isNull(suite)) {
            ProductDO product = productMapper.selectById(productId);
            List<TestsuiteNode> content = new ArrayList<>();
            content.add(new TestsuiteNode(IdUtil.fastSimpleUUID(), product.getName(), "folder", null));
            suite = new TestsuiteDO().setProductId(productId).setContent(JsonUtils.toJsonString(content));
            suiteMapper.insert(suite);
        }
        return TestsuiteConvert.INSTANCE.convert(suite);
    }

    @Override
    public void updateTestsuite(TestsuiteVO testsuite) {
        TestsuiteDO suite = TestsuiteConvert.INSTANCE.convert(testsuite);
        suiteMapper.updateById(suite);
    }
}
