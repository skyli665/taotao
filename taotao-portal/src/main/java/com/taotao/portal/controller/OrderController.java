package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;

/**
 * @author lyx
 * @version 2019年1月7日 下午5:16:22
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    
    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request,HttpServletResponse response,Model model) {
        List<CartItem> list=cartService.getCartItemList(request, response);
        model.addAttribute("cartList", list);
        return "order-cart";
    }
    
    @RequestMapping("/create")
    public String orderCreate(Order order,Model model) {
        try {
       String orderId= orderService.createOrder(order);
       model.addAttribute("orderId", orderId);
       model.addAttribute("payment", order.getPayment());
       model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
       return "success";
        }catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "创建订单出错");
            return "error/excrption";
        }
    }
}
