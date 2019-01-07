package com.taotao.portal.service;
/**
 * @author lyx
 * @version 2019年1月7日 上午9:37:31
 */

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

public interface CartService {
    TaotaoResult addCartItem(long itemId,int num,HttpServletRequest request,HttpServletResponse response);
    List<CartItem> getCartItemList(HttpServletRequest request,HttpServletResponse response);
    TaotaoResult deleteCartItem(long id,HttpServletRequest request, HttpServletResponse response);
}
