package com.bobooi.mall.data.entity.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author bobo
 * @date 2021/8/2
 */

@Data
@Entity
@Table(name="customer_addr")
@NoArgsConstructor
@AllArgsConstructor
public class CsmAddr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerAddrId;
    private Integer customerId;
    private Integer zipcode;
    private String address;
    private Integer isDefault;
}
