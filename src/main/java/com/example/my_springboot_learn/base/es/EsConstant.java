package com.example.my_springboot_learn.base.es;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author : ZhangDingHui
 * @date : Created int 2:19 PM 2019/11/27
 */
@Data
//@Component
public class EsConstant {

    @Value("${elasticSearch.clusterName}")
    public String clusterName;

    @Value("${elasticSearch.esIp}")
    public String esIp;

    @Value("${elasticSearch.esPort}")
    public Integer esPort;

    @Value("${elasticSearch.index}")
    public String index;

    @Value("${elasticSearch.type}")
    public String type;

    @Value("${elasticSearch.trend.index}")
    public String trendIndex;

}
