package com.bobooi.mall.data.service.concrete.search;

import com.bobooi.mall.data.entity.search.ESProduct;
import com.bobooi.mall.data.repository.concrete.search.EsProductRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author bobo
 * @date 2021/8/11
 */

@Service
public class ESProductService {
    @Resource
    private EsProductRepository esProductRepository;

    public List<ESProduct> findAllByKeyWord(String keyword){
        return esProductRepository.findByProductNameOrSupplierNameOrCategoryNameOrProductTypeNameOrDescription(keyword,keyword,keyword,keyword,keyword);
    }

    public List<ESProduct> findAllByAccMatch(String productName,String supplierName, String categoryName, String productTypeName, String description){
        return esProductRepository.findByProductNameAndSupplierNameAndCategoryNameAndProductTypeNameAndDescription(productName,supplierName,categoryName,productTypeName,description);
    }

    public List<ESProduct> findAllByPartMatch(String productName,String supplierName, String categoryName, String productTypeName, String description){
        return esProductRepository.findByProductNameOrSupplierNameOrCategoryNameOrProductTypeNameOrDescription(productName,supplierName,categoryName,productTypeName,description);
    }
}
