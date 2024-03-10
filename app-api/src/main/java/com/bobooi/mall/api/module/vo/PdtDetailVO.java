package com.bobooi.mall.api.module.vo;

import com.bobooi.mall.data.bo.ProductTypeBO;
import com.bobooi.mall.data.entity.product.PdtDetailInf;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;


/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/5
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PdtDetailVO {
    private Integer productId;
    private String productName;
    private String picUrl;
    private Float price;
    private Integer inventory;
    private String description;
    private String supplierName;
    private List<ProductTypeBO> productTypeList;
    private String supplierAddress;
    private Timestamp restTime;

    public static PdtDetailVO fromPdtDetailInfListAndProductTypeList(List<PdtDetailInf> pdtDetailInfList, List<ProductTypeBO> productTypeList) {
        PdtDetailInf firstPdtDetailInf=pdtDetailInfList.get(0);
        return PdtDetailVO.builder()
                .productTypeList(productTypeList)
                .productId(firstPdtDetailInf.getProductId())
                .productName(firstPdtDetailInf.getProductName())
                .picUrl(firstPdtDetailInf.getPicUrl())
                .price(firstPdtDetailInf.getPrice())
                .inventory(firstPdtDetailInf.getInventory())
                .description(firstPdtDetailInf.getDescription())
                .supplierName(firstPdtDetailInf.getSupplierName())
                .supplierAddress(firstPdtDetailInf.getSupplierAddress())
                .restTime(firstPdtDetailInf.getRestTime())
                .build();
    }
}
