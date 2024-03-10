package com.bobooi.mall.data.repository.concrete.product;

import com.bobooi.mall.data.entity.product.SupplierInf;
import com.bobooi.mall.data.repository.DataRepository;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
public interface SupplierInfoRepository extends DataRepository<SupplierInf,Integer> {
    /**
     * 根据供应商id查找供应商信息
     *
     * @param supplierId 供应商id
     * @return
     */
    SupplierInf findBySupplierId(Integer supplierId);

    /**
     * 根据供应商名字和地址查找供应商信息
     *
     * @param supplierName 供应商名字
     * @param address 供应商地址
     * @return
     */
    SupplierInf findBySupplierNameAndAddress(String supplierName,String address);
}
