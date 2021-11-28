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

package xyz.migoo.agiletester.service.tester.testcase;

import xyz.migoo.agiletester.controller.tester.testcase.vo.TestsuiteVO;

/**
 * @author xiaomi
 * Created on 2021/11/27 13:27
 */
public interface TestsuiteService {

    /**
     * 获取测试集合树
     *
     * @param productId 产品id
     * @return 测试集合树
     */
    TestsuiteVO getTestsuite(Long productId);

    /**
     * 更新测试集合树
     *
     * @param testsuite 测试集合树
     */
    void updateTestsuite(TestsuiteVO testsuite);


}
