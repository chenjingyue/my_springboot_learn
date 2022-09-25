package com.redis_client.parser;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArraysParser extends BaseParser {


    @Override
    public boolean canParse(byte value) {

        return value == REPLY_TYPE_ARRAYS;
    }

    @Override
    public Object doParse() {
        return null;
    }
}
