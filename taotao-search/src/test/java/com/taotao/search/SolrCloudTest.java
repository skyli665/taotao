package com.taotao.search;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author lyx
 * @version 2019年1月10日 下午1:33:22
 */
public class SolrCloudTest {
    @Test
    public void testAddDocument() throws Exception{
        //创建连接
        String zkHost="192.168.0.54:2181,192.168.0.54:2182,192.168.0.54:2183";
        CloudSolrServer solrServer=new CloudSolrServer(zkHost);
        //设置默认的collection
        solrServer.setDefaultCollection("collection2");
        //创建文档对象
        SolrInputDocument document=new SolrInputDocument();
        //向文档中添加域
        document.addField("id", "test001");
        document.addField("item_title", "ceshishangpin");
        //文档添加索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }
}
