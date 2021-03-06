package com.redis_client.parser;

import org.apache.commons.lang3.StringUtils;

public class BulkStringsParser extends BaseParser {


    @Override
    public boolean canParse(byte value) {

        return value == REPLY_TYPE_BULK_STRINGS;
    }

    @Override
    public Object doParse() {

        return null;
    }
}
