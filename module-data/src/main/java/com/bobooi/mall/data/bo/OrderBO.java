package com.bobooi.mall.data.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bobo
 * @date 2021/8/8
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBO {
    private double districtMoney;
    private Integer productId;
    private Integer productAmount;
    private Integer productTypeId;
    private Integer customerId;
}
