package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;

/**
* @author lyx
* @version 2019年1月3日 上午10:01:53
*/
@Repository
public class SearchDaoImpl implements SearchDao {
    @Autowired
    private SolrServer solrServer;
    @Override
    public SearchResult search(SolrQuery solrQuery) throws Exception {
        SearchResult result=new SearchResult();
        QueryResponse queryResponse=solrServer.query(solrQuery);
        SolrDocumentList solrDocumentList=queryResponse.getResults();
        result.setRecordCount(solrDocumentList.getNumFound());
        List<Item> itemList=new ArrayList<>();
        Map<String, Map<String, List<String>>> highlighting=queryResponse.getHighlighting();
        for (SolrDocument solrDocument : solrDocumentList) {
            Item item=new Item();
            item.setId((String)solrDocument.get("id"));
            List<String> list=highlighting.get(solrDocument.get("id")).get("item_title");
            String title="";
            if(list!=null&&list.size()>0) {
                title=list.get(0);
            }else {
                title=(String)solrDocument.get("item_title");
            }
            item.setTitle(title);
            item.setImage((String)solrDocument.get("item_image"));
            item.setPrice((long)solrDocument.get("item_price"));
            item.setSell_point((String)solrDocument.get("item_sell_point"));
            item.setCategory_name((String)solrDocument.get("item_category_name"));
            itemList.add(item);
        }
        result.setItemList(itemList);
        return result;
    }

}
