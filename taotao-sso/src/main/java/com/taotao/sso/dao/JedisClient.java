package com.taotao.sso.dao;

public interface JedisClient {
	String get(String key);
	String set(String key,String value);
	String hget(String hKey,String key);
	Long hset(String hKey,String key,String value);
	long incr(String key);
	long expire(String key,int second);
	long ttl(String key);
	long del(String key);
	long hdel(String hKey,String key);
}
