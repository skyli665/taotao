package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.ItemService;

/**   
* Filename:ItemController.java   
* Copyright:Copyright (c)2018  
* Company:com.  
* Author:liyuxin
* @version:1.0   
* @since:JDK 1.8.0_21  
* Create at:2019年1月2日 下午5:31:44   
* Description:  
*   
* Modification History:   
* Date    Author      Version     Description   
* ----------------------------------------------------------------- 
* 2019年1月2日 daniel      1.0     1.0 Version   
*/
@Controller
@RequestMapping("/manager")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/importall")
    @ResponseBody
    public TaotaoResult importAllItems() {
        return itemService.importAllItems();
    }
}
  