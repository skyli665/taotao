package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;

/**
 * 商品信息管理
* @author lyx
* @version 2019年1月4日 下午1:29:31
*/
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${REDIS_ITEM_EXPIRE}")
    private Integer REDIS_ITEM_EXPIRE;
    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    
    @Override
    public TaotaoResult getItemBaseInfo(long itemId) {
        //缓存逻辑
        //从缓存中取
        try {
            String json= jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":base");
            if(!StringUtils.isBlank(json)) {
                //字符串不为空
               TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
               return TaotaoResult.ok(tbItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据id查询
        TbItem item=itemMapper.selectByPrimaryKey(itemId);
        //商品信息写入缓存
        //设置key的有效期
        try {
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":base", JsonUtils.objectToJson(item));
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":base", REDIS_ITEM_EXPIRE);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok(item);
    }


    @Override
    public TaotaoResult getItemDesc(long itemId) {
        //添加缓存
        try {
            String json= jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":desc");
            if(!StringUtils.isBlank(json)) {
                //字符串不为空
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
               return TaotaoResult.ok(itemDesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemDesc itemDesc=itemDescMapper.selectByPrimaryKey(itemId);
        try {
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":desc", JsonUtils.objectToJson(itemDesc));
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":desc", REDIS_ITEM_EXPIRE);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok(itemDesc);
    }


    @Override
    public TaotaoResult getItemParam(long itemId) {
        try {
            String json= jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":param");
            if(!StringUtils.isBlank(json)) {
                //字符串不为空
                TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
               return TaotaoResult.ok(paramItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemParamItemExample example=new TbItemParamItemExample();
        Criteria criteria=example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list=itemParamItemMapper.selectByExampleWithBLOBs(example);
        if(list!=null&&list.size()>0) {
            TbItemParamItem paramItem=list.get(0);
            try {
                jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":param", JsonUtils.objectToJson(paramItem));
                jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":param", REDIS_ITEM_EXPIRE);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return TaotaoResult.ok(paramItem);
        }
        return TaotaoResult.build(400, "无商品信息");
    }

}
