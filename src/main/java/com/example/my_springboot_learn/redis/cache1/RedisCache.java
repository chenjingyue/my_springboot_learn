/*

package com.example.my_springboot_learn.redis.cache1;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RedisCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final String id; // cache instance id

    private RedisTemplate<Object, Object> redisTemplate;

    private static final long EXPIRE_TIME_IN_MINUTES = 30; // redis过期时间


    public RedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    */
/**
     * Put query result to redis
     *
     * @param key
     * @param value
     *//*


    @Override
    public void putObject(Object key, Object value) {
        try {
            RedisTemplate<Object, Object> redisTemplate = getRedisTemplate();
            ValueOperations opsForValue = redisTemplate.opsForValue();
            opsForValue.set(key, value, EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
            System.out.println("putObject:*************");
            System.out.println("key: " + key);
            System.out.println("value: " + value);
            logger.info("Put query result to redis");
        } catch (Throwable t) {
            logger.error("Redis put failed", t);
        }
    }

    */
/**
     * Get cached query result from redis
     *
     * @param key
     * @return
     *//*


    @Override
    public Object getObject(Object key) {
        try {

            RedisTemplate redisTemplate = getRedisTemplate();
            ValueOperations opsForValue = redisTemplate.opsForValue();
            logger.info("Get cached query result from redis");
            System.out.println("getObject:*************");
            System.out.println("key: " + key);
//            System.out.println("****"+opsForValue.get(key).toString());
            return opsForValue.get(key);
        } catch (Throwable t) {
            logger.error("Redis get failed, fail over to db", t);
            return null;
        }
    }

    */
/**
     * Remove cached query result from redis
     *
     * @param key
     * @return
     *//*


    @Override
    @SuppressWarnings("unchecked")
    public Object removeObject(Object key) {
        try {
            RedisTemplate redisTemplate = getRedisTemplate();
            redisTemplate.delete(key);
            logger.info("Remove cached query result from redis");
        } catch (Throwable t) {
            logger.error("Redis remove failed", t);
        }
        return null;
    }

    */
/*
     * Clears this cache instance
     *//*


    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return null;
        });
        logger.info("Clear all the cached query result from redis");
    }

    */
/**
     * This method is not used
     *
     * @return
     *//*


    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }




    private RedisTemplate<Object, Object> getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = ApplicationContextHolder.getBean("redisTemplate");
        }
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

}

*/