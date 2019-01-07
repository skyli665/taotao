package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

/**
 * 购物车
 * 
 * @author lyx
 * @version 2019年1月7日 上午9:49:40
 */
@Service
public class CartServiceImpl implements CartService {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;

    @Override
    public TaotaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> itemList = getCartItemList(request,response);
        CartItem cartItem = null;
        for (CartItem cItem : itemList) {
            if (cItem.getId() == itemId) {
                cItem.setNum(cItem.getNum()+num);
                cartItem=cItem;
                break;
            }
        }
        if (cartItem == null) {
            cartItem=new CartItem();
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItem.class);
            if (taotaoResult.getStatus() == 200) {
                TbItem item = (TbItem) taotaoResult.getData();
                cartItem.setId(item.getId());
                cartItem.setTitle(item.getTitle());
                cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
                cartItem.setNum(num);
                cartItem.setPrice(item.getPrice());
                itemList.add(cartItem);
            }
        }
        CookieUtils.setCookie(request, response, "TT_CART",JsonUtils.objectToJson(itemList) ,true);
        return TaotaoResult.ok();
    }

    /**
     * 去购物车信息
     */
    public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
        String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);
        if(cartJson==null) {
            return new ArrayList<CartItem>();
        }
        try {
            return JsonUtils.jsonToList(cartJson, CartItem.class);
        }catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<CartItem>();
        }
    }

    @Override
    public TaotaoResult deleteCartItem(long id,HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> list = getCartItemList(request, response);
        for (CartItem cartItem : list) {
            if(cartItem.getId()==id) {
                list.remove(cartItem);
                break;
            }
        }
        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), true);
        return TaotaoResult.ok();
    }

}
