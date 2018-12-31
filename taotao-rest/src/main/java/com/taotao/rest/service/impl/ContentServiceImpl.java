package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;

/**
 * 内容获取
 * @author lyx
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	@Override
	public List<TbContent> getContentsList(long contentCid) {
		try {
			String result=jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentCid+"");
			if(!StringUtils.isBlank(result)) {
				return JsonUtils.jsonToList(result, TbContent.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example=new TbContentExample();
		Criteria criteria=example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCid);
		List<TbContent> list=contentMapper.selectByExample(example);
		try {
			//把List转换成字符串
			String cacheString=JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentCid+"", cacheString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
