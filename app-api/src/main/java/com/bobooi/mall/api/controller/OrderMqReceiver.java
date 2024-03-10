package com.bobooi.mall.api.controller;
import com.bobooi.mall.common.utils.jackson.JsonUtils;
import com.bobooi.mall.data.bo.OrderBO;
import com.bobooi.mall.data.config.redis.RedisUtil;
import com.bobooi.mall.data.service.concrete.OrderService;
import com.bobooi.mall.data.service.concrete.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author bobo
 * @date 2021/8/8
 */



/**
 * @author bobo
 * @date 2021/8/8
 */

@Slf4j
@Component
@RabbitListener(queues = "orderQueue")
public class OrderMqReceiver {
    @Resource
    OrderService orderService;
    @Resource
    UserService userService;

    @RabbitHandler
    public void process(String message) {
        OrderBO orderBO = JsonUtils.parseObject(message, OrderBO.class);
        try {
            orderService.addOrder(orderBO.getDistrictMoney(),
                    orderBO.getProductId(),
                    orderBO.getProductAmount(),
                    orderBO.getProductTypeId(),
                    orderBO.getCustomerId(),
                    userService.getUserDefaultAddr(orderBO.getCustomerId()).getCustomerAddrId());
        } catch (Exception e) {
            Integer productId = orderBO.getProductId();
            RedisUtil.incrementKey("product" + productId);
            if(orderService.getGoodSecMap().get(productId)!=null){
                orderService.getGoodSecMap().remove(productId);
            }
            log.info("创建订单失败：",e);
        }
    }

}
