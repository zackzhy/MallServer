package com.bobooi.mall.api.controller;

import com.bobooi.mall.common.response.ApplicationResponse;
import com.bobooi.mall.data.entity.search.ESProduct;
import com.bobooi.mall.data.service.concrete.search.ESProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author bobo
 * @date 2021/8/11
 */

@CrossOrigin("*")
@RestController
@Api(tags = "商品搜索")
@RequestMapping("/search")
public class SearchController {
    @Resource
    private ESProductService esProductService;

    @ApiOperation("商城首页搜索栏模糊搜索")
    @GetMapping
    public ApplicationResponse<List<ESProduct>> getKeywordsMatch(String keyword) {
        List<ESProduct> productList = esProductService.findAllByKeyWord(keyword).stream()
                .filter(esProduct -> esProduct.getRestTime()==null)
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ESProduct::getProductId)))
                        , ArrayList::new));
        return ApplicationResponse.succeed(productList);
    }

    @ApiOperation("每个字段均满足模糊匹配")
    @GetMapping("/accMatch")
    public ApplicationResponse<List<ESProduct>> getAccMatch(String productName,String supplierName, String categoryName, String productTypeName, String description) {
        return ApplicationResponse.succeed(esProductService.findAllByAccMatch(productName,supplierName,categoryName,productTypeName,description));
    }

    @ApiOperation("任一字段满足模糊匹配")
    @GetMapping("/partMatch")
    public ApplicationResponse<List<ESProduct>> getPartMatch(String productName,String supplierName, String categoryName, String productTypeName, String description) {
        return ApplicationResponse.succeed(esProductService.findAllByPartMatch(productName,supplierName,categoryName,productTypeName,description));
    }
}
