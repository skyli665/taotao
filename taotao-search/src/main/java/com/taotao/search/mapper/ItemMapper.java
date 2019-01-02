package com.taotao.search.mapper;   
/**   
* Filename:ItemMapper.java   
* Copyright:Copyright (c)2018  
* Company:com.  
* Author:liyuxin
* @version:1.0   
* @since:JDK 1.8.0_21  
* Create at:2019年1月2日 下午4:59:29   
* Description:  
*   
* Modification History:   
* Date    Author      Version     Description   
* ----------------------------------------------------------------- 
* 2019年1月2日 daniel      1.0     1.0 Version   
*/

import java.util.List;

import com.taotao.search.pojo.Item;

public interface ItemMapper {
    List<Item> getItemList();
}
  