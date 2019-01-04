package com.taotao.rest.service;
/**
* @author lyx
* @version 2019年1月4日 下午1:27:39
*/

import com.taotao.common.pojo.TaotaoResult;

public interface ItemService {
    TaotaoResult getItemBaseInfo(long itemId);
    TaotaoResult getItemDesc(long itemId);
    TaotaoResult getItemParam(long itemId);
}
