package com.netty.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.net.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * https://www.apispace.com/
 */
public class ApiSpace {

    private static RestTemplate restTemplate = null;

    private static RestTemplate getRestTemplate() {
        if (null == restTemplate) {
            restTemplate = new RestTemplate();
        }
        return restTemplate;
    }

    /**
     * 名人名言
     *
     * @return
     */
    public static <T> T mingRenMingYan(int count, Class<T> responseType) {
        RestTemplate restTemplate = getRestTemplate();

        // 封装请求header
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-APISpace-Token", "kxowbu2zlod6fwugxhvmad3bqe816wo2");
        headers.add("Authorization-Type", "apikey");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(null, headers);

        // uri参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page_size", count);
//        String uri = "https://eolink.o.apispace.com/mingrenmingyan/api/v1/ming_ren_ming_yan/random";
        String uri = "https://eolink.o.apispace.com/mingrenmingyan/api/v1/ming_ren_ming_yan/random?page_size={page_size}";

        // 发送请求
        ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET, entity, responseType, paramMap);
        T body = response.getBody();

        return body;

    }

    public static void main(String[] args) {
        int count = 10;
        for (int i = 0; i < count; i++) {
            long startTime = System.currentTimeMillis();
            JSONObject jsonObject = mingRenMingYan(11, JSONObject.class);
            long endTime = System.currentTimeMillis();
            System.out.println("use time:" + (endTime - startTime));
//            System.out.println(jsonObject);
//            JSONArray data = jsonObject.getJSONArray("data");

//            System.out.println(data.size());
        }
    }
}
