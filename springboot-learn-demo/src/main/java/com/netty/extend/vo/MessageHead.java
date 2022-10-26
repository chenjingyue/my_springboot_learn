package com.netty.extend.vo;

import org.msgpack.annotation.Message;

import java.util.HashMap;
import java.util.Map;

@Message
public class MessageHead {

    // 消息类型
    private byte type;

    // 消息id
    private String id;

    // 扩展字段
    private Map<String, Object> extendAttr = new HashMap<>();

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getExtendAttr() {
        return extendAttr;
    }

    public void setExtendAttr(Map<String, Object> extendAttr) {
        this.extendAttr = extendAttr;
    }

    @Override
    public String toString() {
        return "MessageHead{" +
                "type=" + type +
                ", id='" + id + '\'' +
                ", extendAttr=" + extendAttr +
                '}';
    }
}
