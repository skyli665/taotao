package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.util.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.impl.UserServiceImpl;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private UserServiceImpl userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//执行之前，决定是否执行
		//判断用户是否登录
		//从cookie中取token
		String token=CookieUtils.getCookieValue(request, "TT_TOKEN");
		//根据token换取用户信息，调用sso的接口
		TbUser user=userService.getUserByToken(token);
		//取不到信息就跳转到登录页面，带着用户请求的url作为参数
		if(null==user) {
			response.sendRedirect(userService.SSO_BASE_UEL+userService.SSO_PAGE_LOGIN+"?redirect="+request.getRequestURL());
			return false;
		}
		//返回false
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//执行之后

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//返回之后

	}

}
