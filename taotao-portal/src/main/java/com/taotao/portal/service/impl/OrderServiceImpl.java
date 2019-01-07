package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.common.util.JsonUtils;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;

/**
 * @author lyx
 * @version 2019年1月7日 下午5:48:52
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;
    @Value("${ORDER_CREATE_URL}")
    private String ORDER_CREATE_URL;
    
    @Override
    public String createOrder(Order order) {
        String json=HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_URL, JsonUtils.objectToJson(order));
        TaotaoResult taotaoResult=TaotaoResult.format(json);
        if(taotaoResult.getStatus()==200) {
            Long orderId=(Long)taotaoResult.getData();
            return orderId.toString();
        }
        return null;
    }

}
