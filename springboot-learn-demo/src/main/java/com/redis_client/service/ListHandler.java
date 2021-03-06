package com.redis_client.service;

import com.redis_client.client.RedisConnect;

import java.util.List;

public class ListHandler {

    RedisConnect redisConnect = null;


    public List lrange(String key, int start, int stop) {
        String command = "*4" + "\r\n" +
                "$6" + "\r\n" +
                "LRANGE" + "\r\n" +
                "$" + key.getBytes().length + "\r\n" +
                key + "\r\n" +
                "$" + String.valueOf(start).getBytes().length + "\r\n" +
                start + "\r\n" +
                "$" + String.valueOf(stop).getBytes().length + "\r\n" +
                stop + "\r\n";
        Object result = redisConnect.executeCommand(command);
//        Object execute = this.parseExecutor.execute(result);
        System.out.println(result);
        return (List) result;
    }
}
