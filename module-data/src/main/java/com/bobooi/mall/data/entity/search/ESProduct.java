package com.bobooi.mall.data.entity.search;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;


/**
 * @author bobo
 * @date 2021/8/11
 */

@Data
// 指定实体类对应ES的索引名称为blog，类型type是文档类型，使用服务器远程配置
// 为避免每次重启项目都将ES中的数据删除后再同步，createIndex指定为false
@Document(indexName = "product", useServerConfiguration = true, createIndex = false)
public class ESProduct {
    @Id
    private Long id;

    @Field(type = FieldType.Long, name = "product_id")
    private Integer productId;

    @Field(type = FieldType.Text, analyzer = "simple", name = "category_name")
    private String categoryName;

    @Field(type = FieldType.Text, analyzer = "simple", name = "product_name")
    private String productName;

    @Field(type = FieldType.Text, analyzer = "simple")
    private String description;

    @Field(type = FieldType.Text, name = "pic_url")
    private String picUrl;

    @Field(type = FieldType.Float)
    private Float price;

    @Field(type = FieldType.Long)
    private Integer inventory;

    @Field(type = FieldType.Text, analyzer = "simple", name="supplier_name")
    private String supplierName;

    @Field(type = FieldType.Text, analyzer = "simple", name="product_type_name")
    private String productTypeName;

    @Field(type = FieldType.Text, analyzer = "simple", name = "supplier_address")
    private String supplierAddress;

    @Field(type = FieldType.Date, name="rest_time")
    private Date restTime;
}
