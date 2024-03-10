package com.bobooi.mall.data.entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
@Data
@Entity
@Table(name="product_info")
@NoArgsConstructor
@AllArgsConstructor
public class PdtInf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    private Integer categoryId;
    private Integer supplierId;
    private Float price;
    private String description;
    private Integer inventory;
    private String picUrl;
    private Timestamp restTime;
}
