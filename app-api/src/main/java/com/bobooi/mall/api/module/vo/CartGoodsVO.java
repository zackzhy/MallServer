package com.bobooi.mall.api.module.vo;

import com.bobooi.mall.data.bo.CartGoodsBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bobo
 * @date 2021/8/4
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartGoodsVO {
    private Integer cartGoodsId;
    private String picUrl;
    private String productName;
    private String productTypeName;
    private Integer productAmount;
    private Float price;

    public static CartGoodsVO fromCartGoodsBO(CartGoodsBO cartGoodsBO){
        return CartGoodsVO.builder()
                .cartGoodsId(cartGoodsBO.getCartGoods().getCartGoodsId())
                .picUrl(cartGoodsBO.getPdtInf().getPicUrl())
                .productName(cartGoodsBO.getPdtInf().getProductName())
                .productTypeName(cartGoodsBO.getPdtType().getProductTypeName())
                .productAmount(cartGoodsBO.getCartGoods().getProductAmount())
                .price(cartGoodsBO.getPdtInf().getPrice())
                .build();
    }
}
