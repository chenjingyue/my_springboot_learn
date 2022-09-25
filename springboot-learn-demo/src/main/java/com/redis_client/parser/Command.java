package com.redis_client.parser;


import com.redis_client.utils.SafeEncoder;

public enum Command {

    PING,
    AUTH,
    SELECT,
    GET,
    SET,SETNX,SETEX,MSET, QUIT, EXISTS, DEL, TYPE, FLUSHDB, KEYS,LRANGE;
    public final byte[] raw;

    Command() {
        raw = SafeEncoder.encode(this.name());
    }
}
