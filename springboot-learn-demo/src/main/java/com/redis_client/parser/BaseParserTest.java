package com.redis_client.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BaseParserTest {

    public static final byte REPLY_TYPE_SINGLE_ROW = '+';
    public static final byte REPLY_TYPE_ERROR = '-';
    public static final byte REPLY_TYPE_INTEGER = ':';
    public static final byte REPLY_TYPE_BULK_STRINGS = '$';
    public static final byte REPLY_TYPE_ARRAYS = '*';

    private byte[] buf = new byte[11];
    private int limit = 0;
    private int count = 0;
    InputStream input = null;

    public BaseParserTest(InputStream input) {
        this.input = input;
    }

    public Object parse() {
        byte readByte = readByte();
        if (readByte == REPLY_TYPE_SINGLE_ROW) {
            return processSingleRow();
        } else if (readByte == REPLY_TYPE_BULK_STRINGS) {
            return processBulkReply();
        } else if (readByte == REPLY_TYPE_ARRAYS) {
            return processMultiBulkReply();
        } else if (readByte == REPLY_TYPE_INTEGER) {
            return processInteger();
        } else if (readByte == REPLY_TYPE_ERROR) {
            processError();
            return null;
        } else {
            throw new RuntimeException("Unknown reply: " + (char) readByte);
        }

    }

    private Object processMultiBulkReply() {
        int len = getIntCrLf();
        if (len == -1) {
            return null;
        }
        List<byte[]> result = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            byte readByte = readByte();
            if (readByte == REPLY_TYPE_BULK_STRINGS) {
                byte[] value = (byte[]) processBulkReply();
                if (null == value) {
                    throw new RuntimeException("获取数据错误");
                }
                result.add(value);
            } else {
                throw new RuntimeException("Unknown reply: " + (char) readByte);
            }
        }
        return result;
    }

    private Object processBulkReply() {
        int len = getIntCrLf();
        if (len == -1) {
            return null;
        }
        byte[] bytes = new byte[len];
        int offset = 0;
        while (offset < len) {
            int length = read(bytes, offset, len);
            offset += length;
        }
        readByte();
        readByte();
        return bytes;
    }

    private int read(byte[] bytes, int offset, int len) {
        ensureFill();
        int length = Math.min((len - offset), (limit - count));

        System.arraycopy(buf, count, bytes, offset, length);
        count += length;
        return length;
    }

    private int getIntCrLf() {
        return (int) readLongCrLf();
    }

    private long readLongCrLf() {
        ensureFill();
        boolean isNeg = buf[count] == '-';
        if (isNeg) {
            count++;
        }
        long value = 0;
        while (true) {
            ensureFill();
            byte b = buf[count++];
            if (b == '\r') {
                ensureFill();
                if (buf[count++] != '\n') {
                    throw new RuntimeException("Unexpected character!");
                }
                break;
            } else {
                value = value * 10 + b - '0';
            }
        }
        return isNeg ? -value : value;
    }

    private Long processInteger() {
        return readLongCrLf();
    }

    private byte[] readLineBytes() {
        ensureFill();
        ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
        while (true) {
            ensureFill();
            byte b = buf[count++];
            if (b == '\r') {
                ensureFill();
                byte c = buf[count++];
                if (c == '\n') {
                    break;
                }
                byteArrayOutput.write(c);
            }
            byteArrayOutput.write(b);
        }

        return byteArrayOutput.toByteArray();
    }

    private Object processSingleRow() {
        return readLineBytes();
    }

    public void processError() {
        byte[] result = readLineBytes();
        String s = new String(result);
        System.out.println("error: "+s);
        throw new RuntimeException(s);
    }

    private void ensureFill() {
        try {
            if (count >= limit) {
                limit = input.read(buf);
                count = 0;
                if (limit == -1) {
                    throw new RuntimeException("没有读到任何东西");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    private byte readByte() {
        ensureFill();
        return buf[count++];
    }
}
