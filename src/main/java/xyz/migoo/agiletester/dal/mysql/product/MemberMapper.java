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

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.agiletester.controller.product.vo.ProductMemberPageReqVo;
import xyz.migoo.agiletester.dal.objectdata.product.ProductMemberDO;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.QueryWrapperX;

/**
 * @author xiaomi
 * Created on 2021/11/21 14:51
 */
@Mapper
public interface MemberMapper extends BaseMapperX<ProductMemberDO> {

    /**
     * 插入产品\项目成员，忽略错误
     *
     * @param member 产品\项目成员信息
     * @return 插入数量
     */
    @Insert({"<script>" +
            "insert ignore into (product_id, user_id, username, enabled, deleted, create_time, " +
            "creator_id, creator_name, update_time, updater_id, updater_name) " +
            "value (#{productId}, #{userId}, #{username}, #{enabled}, #{deleted}, #{createTime}, " +
            "#{creatorId}, #{creatorName}, #{updateTime}, #{updaterId}, #{updaterName});" +
            "</script>"})
    @Override
    int insert(ProductMemberDO member);

    /**
     * 分页查询
     *
     * @param reqVo 分页查询信息
     * @return 分页结果信息
     */
    default PageResult<ProductMemberDO> selectPage(ProductMemberPageReqVo reqVo) {
        return selectPage(reqVo, new QueryWrapperX<ProductMemberDO>()
                .eqIfPresent("id", reqVo.getId())
                .eqIfPresent("product_id", reqVo.getProductId())
                .eqIfPresent("user_id", reqVo.getUserId())
                .likeIfPresent("username", reqVo.getUsername())
                .betweenIfPresent("create_time", reqVo.getBeginTime(), reqVo.getEndTime())
        );
    }
}
