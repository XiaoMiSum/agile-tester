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

package xyz.migoo.agiletester.dal.mysql.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.agiletester.controller.admin.product.vo.ProductPageReqVo;
import xyz.migoo.agiletester.dal.objectdata.product.ProductDO;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.QueryWrapperX;

/**
 * @author xiaomi
 * Created on 2021/11/21 14:51
 */
@Mapper
public interface ProductMapper extends BaseMapperX<ProductDO> {

    /**
     * 通过产品名称查找用户信息
     *
     * @param name 登录名称
     * @return 用户表信息
     */
    default ProductDO selectByName(String name) {
        return selectOne(new QueryWrapper<ProductDO>().eq("name", name));
    }


    /**
     * 分页查询
     *
     * @param reqVO 分页查询信息
     * @return 分页结果信息
     */
    default PageResult<ProductDO> selectPage(ProductPageReqVo reqVO) {
        return selectPage(reqVO, new QueryWrapperX<ProductDO>()
                .eqIfPresent("id", reqVO.getId())
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("team_id", reqVO.getTeamId())
                .eqIfPresent("enable", reqVO.getEnable())
                .betweenIfPresent("create_time", reqVO.getBeginTime(), reqVO.getEndTime())
        );
    }
}
