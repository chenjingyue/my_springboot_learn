package com.cy.springbootlearndemo.base.es;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author : ZhangDingHui
 * @date : Created int 2:15 PM 2019/11/27
 */
//@Component
@Slf4j
public class ElasticsearchConfig {

    @Resource
    EsConstant esConstant;

    @Bean
    private RestHighLevelClient getClient() throws UnknownHostException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(InetAddress.getByName(esConstant.esIp), esConstant.esPort)));
        return client;
    }

}
