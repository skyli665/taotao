package com.taotao.order.controller;
/**
 * @author lyx
 * @version 2019年1月7日 下午3:28:37
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.ExceptionUtil;
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @RequestMapping(value="/create",method=RequestMethod.POST)
    @ResponseBody
    //接收字符串 转json
    public TaotaoResult createOrder(@RequestBody Order order) {
        try {
        return orderService.createOrder(order, order.getOrderItems(), order.getOrderShipping());
        }catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
