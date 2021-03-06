package com.redis_client.client;

import com.redis_client.utils.SafeEncoder;
import com.redis_client.parser.*;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RedisConnect implements Command {


    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    public static final String OK = "OK";
    public static final String REPLY_TYPE_SINGLE_ROW = "+";
    public static final String REPLY_TYPE_ERROR = "-";
    public static final String REPLY_TYPE_INT = ":";
    public static final String REPLY_TYPE_BATCH = "$";
    public static final String REPLY_TYPE_MULTIPLE_BATCH = "*";
    private boolean isValid;
    private Socket socket;
    private ParseExecutor parseExecutor;

    OutputStream outputStream = null;
    InputStream inputStream = null;
    BaseParserTest baseParserTest = null;

    public RedisConnect(String ip, int port) {
        this(ip, port, null);
    }

    public RedisConnect(String ip, int port, String password) {

        connect(ip, port, password);
//        initParserExecutor();
    }

    private void initParserExecutor() {
        this.parseExecutor = new ParseExecutor();
        parseExecutor.addParser(new SimpleStringsParser());
        parseExecutor.addParser(new IntegersParser());
        parseExecutor.addParser(new ErrorsParser());
        parseExecutor.addParser(new BulkStringsParser());
        parseExecutor.addParser(new ArraysParser());
    }

    private void connect(String ip, int port, String password) {
        if (isConnected()) {
            return;
        }
        try {
            socket = new Socket(ip, port);
            socket.setKeepAlive(true);

            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            baseParserTest = new BaseParserTest(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null != password) {
            auth(password);
        }
    }

    public boolean isConnected() {
        return socket != null && socket.isBound() && !socket.isClosed() && socket.isConnected()
                && !socket.isInputShutdown() && !socket.isOutputShutdown();
    }

    public boolean auth(String password) {
//        String command = "*2" + "\r\n" +
//                "$4" + "\r\n" +
//                "auth" + "\r\n" +
//                "$" + password.getBytes().length + "\r\n" +
//                password + "\r\n";
        sendCommand(Command.AUTH, password);
        Object reply = getReply();
        return true;
    }

    public void select(int index) {

    }


    public void sendCommand(Command command) {
        String[] empty = new String[0];
        sendCommand(command, empty);
    }

    public void sendCommand(Command command, String... args) {
        StringBuilder sb = new StringBuilder();
        sb.append("*").append(args.length + 1).append("\r\n")
                .append("$").append(command.raw.length).append("\r\n")
                .append(command.name()).append("\r\n");

        for (String arg : args) {
            sb.append("$").append(arg.length()).append("\r\n")
                    .append(arg).append("\r\n");
        }
        System.out.println("send command is: " + sb.toString());
        try {
            outputStream.write(sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flush() {
        try {
            this.outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getReply() {
        flush();
        Object result = this.baseParserTest.parse();
        if (result instanceof byte[]) {
            System.out.println("reply: " + new String((byte[]) result));
        }
        return result;
    }

    @Override
    public Long append(String key, String value) {
        sendCommand(Command.APPEND, key, value);
        Long reply = getLongReply();
        return reply;
    }

    @Override
    public Long bitcount(String key) {
        sendCommand(Command.BITCOUNT, key);
        Long reply = getLongReply();
        return reply;
    }

    @Override
    public Long bitcount(String key, long start, long end) {
        sendCommand(Command.BITCOUNT, key);
        Long reply = getLongReply();
        return reply;
    }

    @Override
    public Long decr(String key) {
        sendCommand(Command.DECR, key);
        Long reply = getLongReply();
        return reply;
    }

    @Override
    public Long incr(String key) {
        sendCommand(Command.INCR, key);
        Long reply = getLongReply();
        return reply;
    }

    public String set(String key, String value) {
        sendCommand(Command.SET, key, value);
        byte[] reply = (byte[]) getReply();
        return new String(reply);
    }

    public String set(String key, String value, String expx, long time, String nxxx) {
        sendCommand(Command.SET, key, value, expx, String.valueOf(time), nxxx);
        return getStringReply();
    }

    private String getStringReply() {
        byte[] reply = (byte[]) getReply();
        if (null == reply) {
            return null;
        } else {
            return new String(reply);
        }
    }

    public String setex(String key, final int seconds, final String value) {
        sendCommand(Command.SETEX, key, String.valueOf(seconds), value);
        byte[] reply = (byte[]) getReply();
        return new String(reply);
    }

    public Long setnx(String key, String value) {
        sendCommand(Command.SETNX, key, value);
        Long reply = getLongReply();
        return reply;
    }

    public String mset(String... keysvalues) {
        sendCommand(Command.MSET, keysvalues);
        byte[] reply = (byte[]) getReply();
        return new String(reply);
    }

    public Boolean exists(String key) {
        sendCommand(Command.EXISTS, key);
        Long reply = getLongReply();
        return reply == 1;
    }

    public String get(String key) {
        sendCommand(Command.GET, key);
        return getStringReply();
    }

    public Long del(String key) {
        sendCommand(Command.DEL, key);
        Long reply = getLongReply();
        return reply;
    }

    private Long getLongReply() {
        return (Long) getReply();
    }

    public Long del(String... key) {
        sendCommand(Command.DEL, key);
        Long reply = getLongReply();
        return reply;
    }

    public List<String> lrange(String key, int start, int stop) {
        sendCommand(Command.LRANGE, key, String.valueOf(start), String.valueOf(stop));
        Object reply = getReply();
        if (null == reply) {
            return Collections.emptyList();
        }
        List<byte[]> list = (List<byte[]>) reply;
        List<String> result = new ArrayList<>(list.size());
        for (byte[] bytes : list) {
            if (null == bytes) {
                result.add(null);
            } else {
                result.add(SafeEncoder.encode(bytes));
            }
        }
        return result;
    }


    public Object executeCommand(String command) {
        Object result = null;
        OutputStream outputStream;
        InputStream inputStream;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            outputStream = socket.getOutputStream();
            outputStream.write(command.getBytes());
            inputStream = socket.getInputStream();
//            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[2];
            int available;
            do {
                int count = inputStream.read(bytes);
                byteArrayOutputStream.write(bytes, 0, count);
                available = inputStream.available();
            }
            while (available != 0);
            String value = byteArrayOutputStream.toString();
//            result = this.parseExecutor.execute(value);
//            result = new String(bytes, 0, count);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            IOUtils.closeQuietly(outputStream);
//            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(byteArrayOutputStream);
        }

        return result;
    }

    public void close() {

        if (isConnected()) {
            try {
                outputStream.flush();
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (this.socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public  enum Command {

        PING,
        AUTH,
        SELECT,
        GET,
        SET, SETNX, SETEX, MSET,
        APPEND,
        BITCOUNT,
        BITFIELD,
        DECR,
        INCR,QUIT, EXISTS, DEL, TYPE, FLUSHDB, KEYS, LRANGE;
        public final byte[] raw;

        Command() {
            raw = SafeEncoder.encode(this.name());
        }
    }

}
