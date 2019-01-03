package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

/**
* @author lyx
* @version 2019年1月3日 上午10:28:11
*/
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDao searchDao;
    @Override
    public SearchResult search(String queryString, int page, int rows) throws Exception{
        SolrQuery query=new SolrQuery();
        query.setQuery(queryString);
        query.setStart((page-1)*rows);
        query.setRows(rows);
        query.set("df", "item_keywords");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        SearchResult searchResult=searchDao.search(query);
        long recordCount=searchResult.getRecordCount();
        long pageConut=recordCount/rows;
        if(recordCount%rows>0) {
            pageConut++;
        }
        searchResult.setPageCount(pageConut);
        searchResult.setCurPage(page);
        return searchResult;
    }

}
