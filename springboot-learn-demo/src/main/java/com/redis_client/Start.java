package com.redis_client;

import com.redis_client.client.RedisConnect;

import java.net.Socket;

public class Start {
    private static String ip = "localhost";
    private static int port = 6379;
    private static String username;
    private static String password = "chenyu123";

    private Socket socket;

    public void run() {

        try {
            RedisConnect redisConnect = new RedisConnect(ip, port, password);
//            redisConnect.lrange("list",0,-1);
//            redisConnect.set("aaa","aaa");
//            redisConnect.set("bbb","bbb","ex",990,"nx");
//            redisConnect.setnx("setnx","setnx");
//            redisConnect.setex("setex",90,"setex");
            redisConnect.mset("111","111","222","222","333","33");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Start start = new Start();
        start.run();
//        byte a = '0';
//        int b = a;
//        System.out.println(b);


//        byte[] aa = new byte[100];
//        aa[0] = 10;
//        aa[1] = 10;
//        aa[2] = 10;
//        System.out.println(aa.length);

//        start.get(conmmand.toString());

    }

//    public void get(String key) {
//
//        OutputStream outputStream = null;
//        InputStream inputStream = null;
//        try {
//            outputStream = socket.getOutputStream();
//            inputStream = socket.getInputStream();
//            IOUtils.write(key, outputStream);
//            outputStream.flush();
//            System.out.println("aaaa");
//            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
//            int count = inputStream.read(buffer);
//            System.out.println(new String(buffer,0,count));
//            System.out.println("ccc");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            IOUtils.closeQuietly(outputStream);
//            IOUtils.closeQuietly(inputStream);
//        }
//
//    }
}
