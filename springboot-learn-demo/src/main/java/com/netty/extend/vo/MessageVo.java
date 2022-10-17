package com.netty.extend.vo;

import org.msgpack.annotation.Message;

@Message
public class MessageVo {

    /** 消息头 */
    private MessageHead head;

    /** 消息体 */
    private Object body;


    public MessageHead getHead() {
        return head;
    }

    public void setHead(MessageHead head) {
        this.head = head;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "MessageVo{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
}
