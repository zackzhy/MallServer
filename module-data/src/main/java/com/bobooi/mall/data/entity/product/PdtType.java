package com.bobooi.mall.data.entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 类描述
 *
 * @author <a href="mailto:873406454@qq.com">Li Hangfei</a>
 * @date 2021/8/4
 */
@Data
@Entity
@Table(name="product_type")
@NoArgsConstructor
@AllArgsConstructor
public class PdtType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productTypeId;
    private String productTypeName;
}
