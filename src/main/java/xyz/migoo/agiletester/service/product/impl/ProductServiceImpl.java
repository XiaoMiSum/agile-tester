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

package xyz.migoo.agiletester.service.product.impl;

import org.springframework.stereotype.Service;
import xyz.migoo.agiletester.controller.product.vo.*;
import xyz.migoo.agiletester.convert.product.ProductConvert;
import xyz.migoo.agiletester.dal.mysql.product.MemberMapper;
import xyz.migoo.agiletester.dal.mysql.product.ProductMapper;
import xyz.migoo.agiletester.dal.objectdata.product.ProductDO;
import xyz.migoo.agiletester.dal.objectdata.product.ProductMemberDO;
import xyz.migoo.agiletester.service.product.ProductService;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import java.util.Objects;

import static xyz.migoo.agiletester.enums.ErrorCodeConstants.PRODUCT_IS_EXISTS;
import static xyz.migoo.agiletester.enums.ErrorCodeConstants.PRODUCT_NOT_EXISTS;

/**
 * @author xiaomi
 * Created on 2021/11/21 14:51
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;
    @Resource
    private MemberMapper memberMapper;

    @Override
    public void createProduct(ProductCreateReqVO reqVo) {
        this.verify(reqVo.getName());
        ProductDO product = ProductConvert.INSTANCE.convert(reqVo);
        productMapper.insert(product);
    }

    @Override
    public void updateProduct(ProductUpdateReqVO reqVo) {
        this.verify(reqVo.getId());
        ProductDO product = ProductConvert.INSTANCE.convert(reqVo);
        productMapper.updateById(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productMapper.deleteById(id);
    }

    @Override
    public PageResult<ProductDO> getProducts(ProductPageReqVo reqVo) {
        return productMapper.selectPage(reqVo);
    }

    @Override
    public PageResult<ProductMemberDO> getMembers(ProductMemberPageReqVo reqVo) {
        return memberMapper.selectPage(reqVo);
    }

    @Override
    public void addMembers(ProductMemberCreateReqVO reqVo) {
        ProductMemberDO member = ProductConvert.INSTANCE.convert(reqVo);
        memberMapper.insert(member);
    }

    @Override
    public void deleteMembers(Long id) {
        memberMapper.deleteById(id);
    }

    @Override
    public void updateProductStatus(ProductUpdateStatusReqVO reqVo) {
        this.verify(reqVo.getId());
        ProductDO product = ProductConvert.INSTANCE.convert(reqVo);
        productMapper.updateById(product);
    }

    private void verify(String name) {
        if (Objects.nonNull(productMapper.selectByName(name))) {
            throw ServiceExceptionUtil.get(PRODUCT_IS_EXISTS);
        }
    }

    private void verify(Long id) {
        if (Objects.isNull(productMapper.selectById(id))) {
            throw ServiceExceptionUtil.get(PRODUCT_NOT_EXISTS);
        }
    }
}
