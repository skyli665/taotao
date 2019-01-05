package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;

/**   
* Filename:ItemParamServiceImpl.java   
* Copyright:Copyright (c)2018  
* Company:com.  
* Author:liyuxin
* @version:1.0   
* @since:JDK 1.8.0_21  
* Create at:2018年11月14日 下午7:41:34   
* Description:  
*   
* Modification History:   
* Date    Author      Version     Description   
* ----------------------------------------------------------------- 
* 2018年11月14日 lyx      1.0     1.0 Version   
*/
@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper ItemParamMapper;
    @Override
    public TaotaoResult getItemParamByCid(long cid) {
        TbItemParamExample example=new TbItemParamExample();
        Criteria criteria=example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> list=ItemParamMapper.selectByExampleWithBLOBs(example);
        if(list !=null && list.size()>0) {
        	return TaotaoResult.ok(list.get(0));
        }
        return TaotaoResult.ok();
    }
	@Override
	public TaotaoResult insertItemParam(TbItemParam itemParam) {
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		ItemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
	}

}
  