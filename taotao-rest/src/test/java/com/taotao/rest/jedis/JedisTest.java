package com.taotao.rest.jedis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**   
* Filename:JedisTest.java   
* Copyright:Copyright (c)2018  
* Company:com.  
* Author:liyuxin
* @version:1.0   
* @since:JDK 1.8.0_21  
* Create at:2018年12月28日 下午6:52:42   
* Description:  
*   
* Modification History:   
* Date    Author      Version     Description   
* ----------------------------------------------------------------- 
* 2018年12月28日 daniel      1.0     1.0 Version   
*/
public class JedisTest {
    @Test
    public void testJsdisSingle() {
        Jedis jedis=new Jedis("172.18.245.137",6379);
        jedis.set("key2", "jedis test");
        String string=jedis.get("key2");
        System.out.println(string);
        jedis.close();
    }
    //连接池
    @Test
    public void testJedisPool() {
        JedisPool pool=new JedisPool("172.18.245.137",6379);
        Jedis jedis=pool.getResource();
        String string=jedis.get("key2");
        System.out.println(string);
        jedis.close();
        pool.close();
    }
}
  