package com.taotao.portal.service;
/**
* @author lyx
* @version 2019年1月3日 上午11:52:57
*/

import com.taotao.portal.pojo.SearchResult;

public interface SearchService {
    SearchResult search(String queryString,int page);
}
