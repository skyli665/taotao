package com.taotao.search.service.impl;

import java.util.List;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.ExceptionUtil;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;

/**   
* Filename:ItemServiceImpl.java   
* Copyright:Copyright (c)2018  
* Company:com.  
* Author:liyuxin
* @version:1.0   
* @since:JDK 1.8.0_21  
* Create at:2019年1月2日 下午5:13:23   
* Description:  
*   
* Modification History:   
* Date    Author      Version     Description   
* ----------------------------------------------------------------- 
* 2019年1月2日 daniel      1.0     1.0 Version   
*/
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;
    
    @Override
    public TaotaoResult importAllItems(){
        try {
        List<Item> list=itemMapper.getItemList();
        for (Item item : list) {
            SolrInputDocument document=new SolrInputDocument();
            document.setField("id", item.getId());
            document.setField("item_title", item.getTitle());
            document.setField("item_sell_point", item.getSell_point());
            document.setField("item_price", item.getPrice());
            document.setField("item_image", item.getImage());
            document.setField("item_category_name", item.getCategory_name());
            document.setField("item_desc", item.getItem_des());
            solrServer.add(document);
        }
        solrServer.commit();
        }catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }

}
  