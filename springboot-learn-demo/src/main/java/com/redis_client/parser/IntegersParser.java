package com.redis_client.parser;

import org.apache.commons.lang3.StringUtils;

public class IntegersParser extends BaseParser {


    @Override
    public boolean canParse(byte value) {

        return (value == REPLY_TYPE_INTEGER);
    }

    @Override
    public Object doParse( ) {
        return null;
    }
}
