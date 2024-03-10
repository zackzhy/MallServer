package com.bobooi.mall.data.entity.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
@Data
@Builder
@Entity
@Table(name="order_master")
@NoArgsConstructor
@AllArgsConstructor
public class OrderMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private Long orderSn;
    private Integer customerId;
    private String orderAddr;
    private String picUrl;
    private String productName;
    private String productTypeName;
    private Integer productCnt;
    private Double orderMoney;
    private Double districtMoney;
    private Double payMoney;
    private Timestamp payTime;
}