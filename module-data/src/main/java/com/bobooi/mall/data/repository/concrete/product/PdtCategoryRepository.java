package com.bobooi.mall.data.repository.concrete.product;

import com.bobooi.mall.data.entity.product.PdtCategory;
import com.bobooi.mall.data.repository.DataRepository;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
public interface PdtCategoryRepository extends DataRepository<PdtCategory,Integer> {
    /**
     * 根据商品分类名查找商品分类信息
     *
     * @param categoryName 商品分类名
     * @return
     */
    PdtCategory findByCategoryName(String categoryName);

    /**
     * 根据商品分类id查找商品分类信息
     *
     * @param categoryId 商品分类id
     * @return
     */
    PdtCategory findByCategoryId(Integer categoryId);
}
