package com.bobooi.mall.data.repository.concrete.product;

import com.bobooi.mall.data.entity.product.OrderMaster;
import com.bobooi.mall.data.repository.DataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
public interface OrderMasterRepository extends DataRepository<OrderMaster,Integer> {
    Page<OrderMaster> findAllByCustomerId(Integer customerId, Pageable pageable);
}
