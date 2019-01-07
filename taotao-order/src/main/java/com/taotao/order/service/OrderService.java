package com.taotao.order.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

/**
 * @author lyx
 * @version 2019年1月7日 下午2:59:11
 */
public interface OrderService {
    TaotaoResult createOrder(TbOrder order,List<TbOrderItem> itemList,TbOrderShipping orderShipping);
}
