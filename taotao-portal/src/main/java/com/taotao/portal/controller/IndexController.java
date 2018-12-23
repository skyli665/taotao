package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.portal.service.ContentService;

/**   
* Filename:IndexController.java   
* Copyright:Copyright (c)2018  
* Company:com.  
* Author:liyuxin
* @version:1.0   
* @since:JDK 1.8.0_21  
* Create at:2018年12月5日 下午8:00:04   
* Description:  
*   
* Modification History:   
* Date    Author      Version     Description   
* ----------------------------------------------------------------- 
* 2018年12月5日 daniel      1.0     1.0 Version   
*/
@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
    @RequestMapping("/index")
    public String showIndex(Model model) {
    	String adJson=contentService.getContentList();
    	model.addAttribute("ad1",adJson);
        return "index";
    }
}
  