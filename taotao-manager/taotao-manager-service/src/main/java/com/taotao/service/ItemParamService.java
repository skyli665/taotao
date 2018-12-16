package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

/**   
* Filename:ItemParamService.java   
* Copyright:Copyright (c)2018  
* Company:com.  
* Author:liyuxin
* @version:1.0   
* @since:JDK 1.8.0_21  
* Create at:2018年11月14日 下午7:38:49   
* Description:  
*   
* Modification History:   
* Date    Author      Version     Description   
* ----------------------------------------------------------------- 
* 2018年11月14日 daniel      1.0     1.0 Version   
*/

public interface ItemParamService {
    TaotaoResult getItemParamByCid(long cid);
    TaotaoResult insertItemParam(TbItemParam itemParam);
}
  