package com.taotao.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientTest {
	@Test
	public void doGet() throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpGet get=new HttpGet("http://www.baidu.com");
		CloseableHttpResponse response=httpClient.execute(get);
		int statusCode=response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		HttpEntity entity=response.getEntity();
		String str=EntityUtils.toString(entity,"utf-8");
		System.out.println(str);
		response.close();
		httpClient.close();
	}
	@Test
	public void doGetWithParam() throws Exception{
		CloseableHttpClient httpClient=HttpClients.createDefault();
		URIBuilder uriBuilder=new URIBuilder("http://www.sogou.com/web");
		uriBuilder.addParameter("query", "花千骨");
		HttpGet get=new HttpGet(uriBuilder.build());
		CloseableHttpResponse response=httpClient.execute(get);
		int statusCode=response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		HttpEntity entity=response.getEntity();
		String str=EntityUtils.toString(entity,"utf-8");
		System.out.println(str);
		response.close();
		httpClient.close();
	}
	public void doPost()throws Exception {
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpPost post=new HttpPost("");
		CloseableHttpResponse response=httpClient.execute(post);
		String str=EntityUtils.toString(response.getEntity());
		System.out.println(str);
		response.close();
		httpClient.close();
	}
}
