package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

/**
 * @author lyx
 * @version 2019年1月7日 下午5:16:22
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private CartService cartService;
    
    
    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request,HttpServletResponse response,Model model) {
        List<CartItem> list=cartService.getCartItemList(request, response);
        model.addAttribute("cartList", list);
        return "order-cart";
    }
}