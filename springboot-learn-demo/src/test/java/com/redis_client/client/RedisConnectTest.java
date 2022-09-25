package com.redis_client.client;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RedisConnectTest {

    public static final String OK = "OK";
    public static final Object NULL = null;
    private RedisConnect redisConnect;
    String key = "test";
    String value = "test";


    @Before
    public void setUp() {
        System.out.println("----run before----");
        redisConnect = new RedisConnect("localhost", 6379, "chenyu123");
        redisConnect.del(key);
    }

    @Test
    public void testAppend() {
        Long result = redisConnect.append(key, value);
        Assert.assertEquals((long) result, value.length());
        testDel();
    }

    @Test
    public void testSet() {
        String result = redisConnect.set(key, value);
        Assert.assertEquals(result, OK);
        testDel();
        result = redisConnect.set(key, value, "ex", 10, "nx");
        Assert.assertEquals(result, OK);
        testDel();
        result = redisConnect.set(key, value, "px", 1000, "nx");
        Assert.assertEquals(result, OK);
        testDel();
        result = redisConnect.set(key, value, "ex", 10, "xx");
        Assert.assertEquals(result, NULL);
        testDel();
        result = redisConnect.setex(key, 10, value);
        Assert.assertEquals(result, OK);
        testDel();
        Long flag = redisConnect.setnx(key, value);
        Assert.assertEquals((long) flag, 1);
        testDel();
    }

    @Test
    public void testGet() {
        redisConnect.set(key, value);
        String result = redisConnect.get(key);
        Assert.assertEquals(result, value);
        testDel();
    }

    @Test
    public void testExists() {
        Boolean result = redisConnect.exists(key);
        Assert.assertEquals(result, false);
        redisConnect.set(key, value);
        result = redisConnect.exists(key);
        Assert.assertEquals(result, true);
        testDel();
    }

    @Test
    public void testBitcount() {
        redisConnect.set(key, value);
        Long result = redisConnect.bitcount(key);
        Assert.assertEquals((long) result, 17L);
        result = redisConnect.bitcount(key, 0, 1);
        Assert.assertEquals((long) result, 8L);
        testDel();
    }

    @Test
    public void testDecr() {
        String numKey = "num";
        redisConnect.set(numKey, "10");
        Long result = redisConnect.decr(numKey);
        Assert.assertEquals((long) result, 9L);
        testDel(numKey);
    }

    @Test
    public void testIncr() {
        String numKey = "num";
        redisConnect.set(numKey, "10");
        Long result = redisConnect.incr(numKey);
        Assert.assertEquals((long) result, 11L);
        testDel(numKey);
    }

    @Test
    public void testIncrException() {
        String numKey = "num";
        redisConnect.set(numKey, "10a");
        try {
            redisConnect.incr(numKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            testDel(numKey);
        }
    }


    public void testDel() {
        redisConnect.del(key);
    }
    public void testDel(String key) {
        redisConnect.del(key);
    }


    @After
    public void tearDown() {
        System.out.println("----run after----");
        redisConnect.del(key);
        redisConnect.close();
    }
}