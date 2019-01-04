package com.taotao.sso.service;
/**
 * @author lyx
 * @version 2019年1月4日 下午5:49:09
 */

import com.taotao.common.pojo.TaotaoResult;

public interface UserService {
    TaotaoResult checkData(String content,Integer type);
}
