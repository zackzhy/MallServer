package com.bobooi.mall.data.repository.concrete.customer;

import com.bobooi.mall.data.entity.customer.CsmAddr;
import com.bobooi.mall.data.repository.DataRepository;

import java.util.List;

/**
 * @author bobo
 * @date 2021/8/6
 */

public interface CsmAddrRepository extends DataRepository<CsmAddr, Integer> {
    /**
     * 根据用户id查找所有地址信息
     *
     * @param customerId 用户id
     * @return
     */
    List<CsmAddr>  findAllByCustomerId(Integer customerId);

    CsmAddr  findByCustomerAddrId(Integer customerAddrId);

    /**
     * 找出用户默认地址
     *
     * @param customerId 用户id
     * @param isDefault 默认地址标识
     * @return
     */
    public  CsmAddr findByCustomerIdAndIsDefault(Integer customerId, Integer isDefault);
}
