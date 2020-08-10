package com.jvm_test.utils;

public class ByteUtil {

    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        int len = bytes.length;
        // 由高位到低位
        for (int i = 0; i < len; i++) {
            int shift = (len - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;// 往高位游
        }
        return value;
    }

    public static byte[] getBytesFromOffset(byte[] bytes, int offset, int len) {
        if (len <= 0) {
            return new byte[0];
        }
        if ((offset + len) > bytes.length) {
            return new byte[0];
        }
        byte[] resultBytes = new byte[len];
        for (int i = offset; i < len + offset; i++) {
            resultBytes[i - offset] = bytes[i];
        }
        return resultBytes;
    }


    public static int readByteArrayToIntFromOffset(byte[] bytes, int offset, int len) {
        return byteArrayToInt(getBytesFromOffset(bytes, offset, len));
    }

    public static int readByteArrayToIntFromOffset(byte[] bytes, int len) {
        return byteArrayToInt(getBytesFromOffset(bytes, 0, len));
    }
}
