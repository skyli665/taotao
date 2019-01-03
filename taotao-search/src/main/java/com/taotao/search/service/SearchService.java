package com.taotao.search.service;
/**
* @author lyx
* @version 2019年1月3日 上午10:26:53
*/

import com.taotao.search.pojo.SearchResult;

public interface SearchService {
    SearchResult search(String queryString,int page,int rows) throws Exception;
}
