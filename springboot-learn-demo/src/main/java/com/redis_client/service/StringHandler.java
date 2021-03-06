package com.redis_client.service;

import com.redis_client.client.RedisConnect;

public class StringHandler {

    RedisConnect redisConnect = null;

    public Object get(String key) {

        String command = "*2" + "\r\n" +
                "$3" + "\r\n" +
                "get" + "\r\n" +
                "$" + key.getBytes().length + "\r\n" +
                key + "\r\n";
        return redisConnect.executeCommand(command);
    }

    public void set(String key, String value) {
        // "*3\r\n$3\r\nSET\r\n$5\r\nmykey\r\n$7\r\nmyvalue\r\n"
        String command = "*3" + "\r\n" +
                "$3" + "\r\n" +
                "SET" + "\r\n" +
                "$" + key.getBytes().length + "\r\n" +
                key + "\r\n" +
                "$" + value.getBytes().length + "\r\n" +
                value + "\r\n";
        Object result = redisConnect.executeCommand(command);
    }
}
