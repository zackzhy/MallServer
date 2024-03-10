package com.bobooi.mall.api.module.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author bobo
 * @date 2021/8/6
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecGoodsVO {
    private GoodsVO goodsInf;
    private Timestamp restTime;

    public static SecGoodsVO fromGoodsVOAndRestTime(GoodsVO goodsInf, Timestamp restTime){
        return SecGoodsVO.builder()
                .goodsInf(goodsInf)
                .restTime(restTime)
                .build();
    }
}
