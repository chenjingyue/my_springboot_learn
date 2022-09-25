package com.redis_client.parser;

import java.io.InputStream;

public interface Parser {

    public static final String OK = "OK";
    public static final byte REPLY_TYPE_SINGLE_ROW = '+';
    public static final byte REPLY_TYPE_ERROR = '-';
    public static final byte REPLY_TYPE_INTEGER = ':';
    public static final byte REPLY_TYPE_BULK_STRINGS = '$';
    public static final byte REPLY_TYPE_ARRAYS = '*';

    boolean canParse(byte value);

    Object parse(InputStream input);

}
