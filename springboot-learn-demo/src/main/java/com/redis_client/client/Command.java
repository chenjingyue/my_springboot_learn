package com.redis_client.client;

import java.util.List;

public interface Command {
    /**
     * Strings 命令
     */
    Long append(String key, String value);

    Long bitcount(String key);

    Long bitcount(String key,long start,long end);
    Long decr(String key);
    Long incr(String key);

    String set(String key, String value);

    String setex(String key, int seconds, String value);

    Long setnx(String key, String value);

    String mset(String... keysvalues);

    String set(String key, String value, String expx, long time, String nxxx);

    String get(String key);

    Boolean exists(String key);

    Long del(String key);

    Long del(String... key);

    /**
     * List 命令
     */
    List<String> lrange(String key, int start, int stop);

}
