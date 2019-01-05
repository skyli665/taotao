package com.taotao.rest.jedis;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
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
        Jedis jedis=new Jedis("192.168.0.53",6379);
        jedis.set("key2", "jedis test");
        String string=jedis.get("key2");
        System.out.println(string);
        jedis.close();
    }
    //连接池
    @Test
    public void testJedisPool() {
    	JedisPool pool=new JedisPool("192.168.0.53",6379);
        Jedis jedis=pool.getResource();
        String string=jedis.get("key2");
        System.out.println(string);
        jedis.close();
        pool.close();
    }
    //测试集群
    
    public void testJsdisCluster() {
    	String ipaddr="192.168.0.53";
    	HashSet<HostAndPort> nodes=new HashSet<>();
    	nodes.add(new HostAndPort(ipaddr, 7001));
    	nodes.add(new HostAndPort(ipaddr, 7002));
    	nodes.add(new HostAndPort(ipaddr, 7003));
    	nodes.add(new HostAndPort(ipaddr, 7004));
    	nodes.add(new HostAndPort(ipaddr, 7005));
    	nodes.add(new HostAndPort(ipaddr, 7006));
    	//自带连接池
    	JedisCluster cluster=new JedisCluster(nodes);
    	cluster.set("key1", "sefsdfadfa");
    	String string=cluster.get("key1");
    	System.out.println(string);
    }
    
    @Test
    public void testSpringJedisSingle() {
    	ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
    	JedisPool pool=(JedisPool)applicationContext.getBean("redisClient");
    	Jedis jedis=pool.getResource();
    	String string=jedis.get("key2");
    	System.out.println(string);
    	jedis.close();
    	pool.close();
    }
}
  