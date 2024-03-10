package com.bobooi.mall.data.repository.concrete.search;

import com.bobooi.mall.data.entity.search.ESProduct;
import com.bobooi.mall.data.repository.ElasticRepository;

import java.util.List;

/**
 * @author bobo
 * @date 2021/8/11
 */

public interface EsProductRepository extends ElasticRepository<ESProduct, Long> {
    List<ESProduct> findByProductNameOrSupplierNameOrCategoryNameOrProductTypeNameOrDescription(
            String productName,String supplierName, String categoryName, String productTypeName, String description);

    List<ESProduct> findByProductNameAndSupplierNameAndCategoryNameAndProductTypeNameAndDescription(
            String productName,String supplierName, String categoryName, String productTypeName, String description);
}
