package com.taotao.sso.service;
/**
 * @author lyx
 * @version 2019年1月4日 下午5:49:09
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {
    TaotaoResult checkData(String content,Integer type);
    TaotaoResult createUser(TbUser user);
    TaotaoResult userlogin(String username,String password,HttpServletRequest request,HttpServletResponse response);
    TaotaoResult getUserByToken(String token);
}
