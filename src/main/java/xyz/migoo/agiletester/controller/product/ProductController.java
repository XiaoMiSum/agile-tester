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

package xyz.migoo.agiletester.controller.product;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.agiletester.controller.product.vo.*;
import xyz.migoo.agiletester.convert.product.ProductConvert;
import xyz.migoo.agiletester.service.product.ProductService;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author xiaomi
 * Created on 2021/11/21 14:51
 */
@Validated
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @PutMapping
    public Result<?> createProduct(@Valid @RequestBody ProductCreateReqVO reqVo) {
        productService.createProduct(reqVo);
        return Result.getSuccessful();
    }

    @PostMapping
    public Result<?> updateProduct(@Valid @RequestBody ProductUpdateReqVO reqVo) {
        productService.updateProduct(reqVo);
        return Result.getSuccessful();
    }

    @DeleteMapping
    public Result<?> deleteProduct(@NotNull(message = "产品编号不能为空") Long id) {
        productService.deleteProduct(id);
        return Result.getSuccessful();
    }

    @GetMapping
    public Result<PageResult<?>> getProducts(ProductPageReqVo reqVo) {
        PageResult<ProductRespVO> result = ProductConvert.INSTANCE.convert(productService.getProducts(reqVo));
        return Result.getSuccessful(result);
    }

    @GetMapping("/member")
    public Result<PageResult<?>> getMembers(@Valid @RequestBody ProductMemberPageReqVo reqVo) {
        PageResult<ProductMemberRespVO> result = ProductConvert.INSTANCE.convert1(productService.getMembers(reqVo));
        return Result.getSuccessful(result);
    }

    @PutMapping("/member")
    public Result<?> addMembers(@Valid @RequestBody ProductMemberCreateReqVO reqVo) {
        productService.addMembers(reqVo);
        return Result.getSuccessful();
    }

    @DeleteMapping("/member")
    public Result<?> deleteMembers(Long id) {
        productService.deleteMembers(id);
        return Result.getSuccessful();
    }
}
