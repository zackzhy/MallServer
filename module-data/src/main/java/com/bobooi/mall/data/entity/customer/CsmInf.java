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
@Table(name="customer_inf")
@NoArgsConstructor
@AllArgsConstructor
public class CsmInf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerInfId;
    private Integer customerId;
    private String customerName;
    private Integer identityCardType;
    private String identityCardNo;
    private String mobilePhone;
    private Character gender;
    private Integer userPoint;
}
