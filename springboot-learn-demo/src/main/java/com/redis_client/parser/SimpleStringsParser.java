package com.redis_client.parser;

import org.apache.commons.lang3.StringUtils;

public class SimpleStringsParser extends BaseParser {

    private String value;

    @Override
    public boolean canParse(byte value) {

        return value == REPLY_TYPE_SINGLE_ROW;
    }

    @Override
    public Object doParse() {
        String replace = value.replace("\r", "").replace("\n", "");



        return replace.substring(1);
    }
}
