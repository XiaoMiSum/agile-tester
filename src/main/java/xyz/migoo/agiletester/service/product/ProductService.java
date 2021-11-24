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

package xyz.migoo.agiletester.service.product;

import xyz.migoo.agiletester.controller.product.vo.*;
import xyz.migoo.agiletester.dal.objectdata.product.ProductDO;
import xyz.migoo.agiletester.dal.objectdata.product.ProductMemberDO;
import xyz.migoo.framework.common.pojo.PageResult;

/**
 * @author xiaomi
 * Created on 2021/11/21 14:51
 */
public interface ProductService {

    /**
     * 创建产品\项目
     *
     * @param reqVo 产品\项目信息
     */
    void createProduct(ProductCreateReqVO reqVo);

    /**
     * 更新产品\项目
     *
     * @param reqVo 产品\项目信息
     */
    void updateProduct(ProductUpdateReqVO reqVo);

    /**
     * 删除产品\项目
     *
     * @param id 产品\项目id
     */
    void deleteProduct(Long id);

    /**
     * 分页获取产品\项目列表
     *
     * @param reqVo 分页查询参数
     * @return 产品\项目列表
     */
    PageResult<ProductDO> getProducts(ProductPageReqVo reqVo);

    /**
     * 分页获取产品\项目成员列表
     *
     * @param reqVo 分页查询参数
     * @return 产品\项目成员列表
     */
    PageResult<ProductMemberDO> getMembers(ProductMemberPageReqVo reqVo);

    /**
     * 添加产品\项目成员
     *
     * @param reqVo 新增信息
     */
    void addMembers(ProductMemberCreateReqVO reqVo);

    /**
     * 删除产品\项目成员
     *
     * @param id 数据id
     */
    void deleteMembers(Long id);
}
