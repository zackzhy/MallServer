package com.bobooi.mall.data.repository.concrete.product;

import com.bobooi.mall.data.entity.product.PdtType;
import com.bobooi.mall.data.repository.DataRepository;

/**
 * @author bobo
 * @date 2021/8/4
 */

public interface PdtTypeRepository extends DataRepository<PdtType,Integer> {
    PdtType findByProductTypeId(Integer productTypeId);
    PdtType findByProductTypeName(String productTypeName);
}
