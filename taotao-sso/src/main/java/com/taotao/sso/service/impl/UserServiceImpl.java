package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;

/**
 * @author lyx
 * @version 2019年1月4日 下午5:50:31
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;
    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;
    
    
    @Override
    public TaotaoResult checkData(String content, Integer type) {
        TbUserExample example=new TbUserExample();
        Criteria criteria=example.createCriteria();
        switch (type) {
        case 1:{criteria.andUsernameEqualTo(content); break;}
        case 2:{criteria.andPhoneEqualTo(content); break;}
        case 3:{criteria.andEmailEqualTo(content); break;}
        default: return TaotaoResult.ok(false);
        }
        List<TbUser> list = userMapper.selectByExample(example);
        if(list==null||list.size()<=0) {
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }
	@Override
	public TaotaoResult createUser(TbUser user) {
		user.setUpdated(new Date());
		user.setCreated(new Date());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult userlogin(String username, String password,HttpServletRequest request,HttpServletResponse response) {
		TbUserExample example=new TbUserExample();
		Criteria criteria=example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list=userMapper.selectByExample(example);
		if (null==list||list.size()==0) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		TbUser user=list.get(0);
		if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		String token=UUID.randomUUID().toString();
		//清理密码信息
		user.setPassword(null);
		jedisClient.set(REDIS_USER_SESSION_KEY+":"+token, JsonUtils.objectToJson(user));
		jedisClient.expire(REDIS_USER_SESSION_KEY+":"+token, SSO_SESSION_EXPIRE);
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		return TaotaoResult.ok(token);
	}
	@Override
	public TaotaoResult getUserByToken(String token) {
		String json=jedisClient.get(REDIS_USER_SESSION_KEY+":"+token);
		if(StringUtils.isBlank(json)) {
			return TaotaoResult.build(400, "session已过期");
		}
		jedisClient.expire(REDIS_USER_SESSION_KEY+":"+token, SSO_SESSION_EXPIRE);
		return TaotaoResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
	}

}
