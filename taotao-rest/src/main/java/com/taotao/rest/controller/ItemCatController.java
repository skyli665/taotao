package com.taotao.rest.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.util.JsonUtils;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

/**
 * 商品分类列表
 * @author lyx
 *
 */
@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	@RequestMapping(value="/itemcat/list",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback) {
		CatResult catResult=itemCatService.getItemCatList();
		String json=JsonUtils.objectToJson(catResult);
		//拼装返回值
		String result=callback+"("+json+");";
		return result;
	}
}
