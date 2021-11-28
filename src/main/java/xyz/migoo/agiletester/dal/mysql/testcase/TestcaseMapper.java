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

package xyz.migoo.agiletester.dal.mysql.testcase;

import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.agiletester.dal.objectdata.testcase.TestcaseDO;
import xyz.migoo.framework.mybatis.core.BaseMapperX;

/**
 * @author xiaomi
 * Created on 2021/11/27 14:18
 */
@Mapper
public interface TestcaseMapper extends BaseMapperX<TestcaseDO> {

    /**
     * 通过suiteId获取用例集
     *
     * @param suiteId suiteId
     * @return 用例集合树
     */
    default TestcaseDO selectBySuite(String suiteId) {
        return this.selectOne("suite_id", suiteId);
    }
}
