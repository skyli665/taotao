package com.taotao.portal.service;
/**
 * @author lyx
 * @version 2019年1月4日 下午2:55:26
 */

import com.taotao.portal.pojo.ItemInfo;

public interface ItemService {
    ItemInfo getItemById(Long itemId);
    String getItemDescById(Long itemId);
    String getItemParam(Long itemId);
}
