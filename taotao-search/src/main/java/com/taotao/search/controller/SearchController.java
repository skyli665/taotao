package com.taotao.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.ExceptionUtil;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

/**
 * @author lyx
 * @version 2019年1月3日 上午10:44:16
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult search(@RequestParam("q") String query, @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "60") Integer rows) {
        if (StringUtils.isBlank(query)) {
            return TaotaoResult.build(400, "查询条件不能为空");
        }

        SearchResult searchResult = null;
        try {
            query = new String(query.getBytes("iso8859-1"), "utf-8");
            searchResult = searchService.search(query, page, rows);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok(searchResult);
    }
}
