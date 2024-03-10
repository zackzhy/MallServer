package com.bobooi.mall.api.module.vo;

import com.bobooi.mall.data.entity.product.PdtInf;
import com.bobooi.mall.data.entity.product.SupplierInf;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVO {
    private Integer id;
    private String productName;
    private String picUrl;
    private Float price;
    private String supplierName;
    private Timestamp restTime;

    public static GoodsVO fromPdtInfAndSupplierInf(PdtInf pdtInf, SupplierInf supplierInfo){
        return GoodsVO.builder()
                .id(pdtInf.getProductId())
                .productName(pdtInf.getProductName())
                .picUrl(pdtInf.getPicUrl())
                .price(pdtInf.getPrice())
                .supplierName(supplierInfo.getSupplierName())
                .restTime(pdtInf.getRestTime())
                .build();
    }
}
