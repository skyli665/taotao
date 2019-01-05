package com.taotao.search.dao;
/**
* @author 作者
* @version 创建时间：2019年1月3日 上午9:56:40
* 类说明
*/

import org.apache.solr.client.solrj.SolrQuery;

import com.taotao.search.pojo.SearchResult;

public interface SearchDao {
    SearchResult search(SolrQuery solrQuery) throws Exception;
}
