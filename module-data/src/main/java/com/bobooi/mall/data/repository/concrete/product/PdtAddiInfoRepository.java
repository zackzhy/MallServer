package com.bobooi.mall.data.repository.concrete.product;

import com.bobooi.mall.data.entity.product.PdtAddiInf;
import com.bobooi.mall.data.repository.DataRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
public interface PdtAddiInfoRepository extends DataRepository<PdtAddiInf,Integer> {
    /**
     * 根据商品id删除关联的详细数据
     *
     * @param productId 商品id
     * @return
     */
    @Transactional
    void deleteAllByProductId(Integer productId);
  
    List<PdtAddiInf> findAllByProductId(Integer productId);
  
    PdtAddiInf findByProductIdAndProductTypeId(Integer productId, Integer productTypeId);
}
