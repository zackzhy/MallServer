package com.bobooi.mall.api.controller;

import com.bobooi.mall.common.exception.ApplicationException;
import com.bobooi.mall.common.exception.AssertUtils;
import com.bobooi.mall.common.response.ApplicationResponse;
import com.bobooi.mall.common.response.SystemCodeEnum;
import com.bobooi.mall.data.bo.PageParam;
import com.bobooi.mall.data.dto.BatchOperationResultDTO;
import com.bobooi.mall.data.entity.product.OrderMaster;
import com.bobooi.mall.data.service.concrete.OrderService;
import com.bobooi.mall.data.service.concrete.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author bobo
 * @date 2021/8/6
 */

@CrossOrigin("*")
@Api(tags = "订单管理")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private UserService userService;

    @ApiOperation("获取所有订单信息【分页！！！！！】")
    @RequiresPermissions(logical = Logical.AND, value = {"csmLogin:*"})
    @GetMapping("/all")
    public ApplicationResponse<List<OrderMaster>> getAllOrders(PageParam pageParam) {
        return ApplicationResponse.succeed(orderService.findAll(pageParam));
    }

    @ApiOperation("获取所有订单总数【！！！！！总数】")
    @RequiresPermissions(logical = Logical.AND, value = {"csmLogin:*"})
    @GetMapping("/all/sum")
    public ApplicationResponse<Long> getAllOrdersSum() {
        return ApplicationResponse.succeed(orderService.getOrdersSum());
    }

    @ApiOperation("获取当前用户订单信息【分页！！！！！】")
    @GetMapping
    public ApplicationResponse<List<OrderMaster>> getAllOrdersByUser(PageParam pageParam) {
        return ApplicationResponse.succeed(orderService.findAllByCustomerId(pageParam));
    }

    @ApiOperation("根据购物车商品id列表生成订单")
    @GetMapping("/generate")
    public ApplicationResponse<BatchOperationResultDTO<Integer>> generateOrder(Integer[] cartGoodsIds, Integer customerAddrId, Integer point) {
        AssertUtils.notNull(cartGoodsIds, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(customerAddrId, new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG));
        AssertUtils.notNull(point, new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG));
        return ApplicationResponse.succeed(
                orderService.addOrders(cartGoodsIds, customerAddrId, point));
    }

    @ApiOperation("根据秒杀商品id生成订单")
    @PostMapping("/generateSec")
    public ApplicationResponse<String> secKill(Integer productId, Integer productTypeId){
        AssertUtils.notNull(productId, new ApplicationException(SystemCodeEnum.ARGUMENT_MISSING));
        AssertUtils.notNull(productTypeId, new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG));
        return orderService.secKill(productId, productTypeId,userService.info().getCustomerId())?
                ApplicationResponse.succeed("创建订单成功"):
                ApplicationResponse.fail(SystemCodeEnum.ARGUMENT_WRONG,"创建订单失败");
    }
}
