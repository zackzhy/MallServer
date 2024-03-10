package com.bobooi.mall.data.repository.concrete.product;

import com.bobooi.mall.data.entity.product.PdtInf;
import com.bobooi.mall.data.repository.DataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
public interface PdtInfoRepository extends DataRepository<PdtInf,Integer> {

    @Query(value = "select * from product_info where inventory!=0 and rest_time IS NULL and category_id=:categoryId",nativeQuery = true)
    Page<PdtInf> findAllByCategoryIdWithoutSec(@Param("categoryId") Integer categoryId, Pageable pageable);

    @Query(value = "select * from product_info where inventory!=0 and rest_time > CURRENT_TIMESTAMP",nativeQuery = true)
    Page<PdtInf> findAllByCategoryIdWithSec(Pageable pageable);

    /**
     * 根据商品id获取商品信息
     *
     * @param productId 商品id
     * @return
     */
    PdtInf findByProductId(Integer productId);

    /**
     * 根据商品id删除商品信息
     *
     * @param productId 商品id
     * @return
     */
    @Transactional
    void deleteByProductId(Integer productId);

    @Modifying
    @Transactional
    @Query(value = "update product_info set inventory=0 where product_id=:productId",nativeQuery=true)
    void updateInventoryByProductId(@Param("productId") Integer productId);
}
