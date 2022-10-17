package com.netty.extend.server;

import com.netty.extend.vo.MessageHead;
import com.netty.extend.vo.MessageType;
import com.netty.extend.vo.MessageVo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageVo messageVo = (MessageVo) msg;
        System.out.println("receive message: " + messageVo);
        MessageVo responseMessage = responseMessage("OK");
        writeAndFlush(ctx, responseMessage);


    }

    private MessageVo responseMessage(Object body) {
        MessageVo msg = new MessageVo();
        MessageHead head = new MessageHead();
        head.setType(MessageType.SERVICE_RESP.value());
        msg.setHead(head);
        msg.setBody(body);
        return msg;
    }

    private void writeAndFlush(ChannelHandlerContext ctx, MessageVo messageVo) {
        ctx.writeAndFlush(messageVo);
    }

    public static void main(String[] args) {
        MessageVo msg = new MessageVo();
        MessageVo msg1 =  msg;
        msg = null;
        System.out.println("aaaaa");
    }
}
