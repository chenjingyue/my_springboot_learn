package com.redis_client.parser;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;

public abstract class BaseParser implements Parser {

    private String value;
    public byte[] buf = new byte[1024 * 4];
    public int limit = 0;
    public int count = 0;

    public abstract boolean canParse(byte value);

    @Override
    public Object parse(InputStream input) {
        byte readByte = readByte(input);
        if(canParse(readByte)) {
            return doParse();
        }
        return null;

//        String replace = value.replace("\r", "").replace("\n", "");
//        return replace.substring(1);
    }
    protected abstract Object doParse();

    private byte readByte(InputStream input){
        try {
            limit = input.read(buf);
            if(limit == -1) {
                throw  new RuntimeException("input stream stop");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf[count++];
    }
}
