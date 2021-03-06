package com.redis_client.parser;

import org.apache.commons.collections.CollectionUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParseExecutor {

    private List<Parser> parserList = new ArrayList<>();

    public ParseExecutor() {

    }

    public ParseExecutor(Collection<Parser> parserList) {
        this.parserList.addAll(parserList);
    }

    public void addParser(Parser parser) {
        if (null == parserList) {
            this.parserList = new ArrayList<>();
        }
        parserList.add(parser);
    }

    public Object execute(InputStream input) {
        if (CollectionUtils.isEmpty(parserList)) {
            return null;
        }
        for (Parser parser : parserList) {
            Object result = parser.parse(input);
            if (null != result) {
                return result;
            }
        }
        return null;
    }
}
