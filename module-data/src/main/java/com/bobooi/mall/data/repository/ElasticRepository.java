package com.bobooi.mall.data.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author bobo
 * @date 2021/8/11
 */

public interface ElasticRepository <Entity, Id> extends ElasticsearchRepository<Entity, Id> {
}
