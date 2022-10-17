package com.netty.extend.server;

import com.netty.extend.vo.MessageHead;
import com.netty.extend.vo.MessageType;
import com.netty.extend.vo.MessageVo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartbeatRespHandler extends SimpleChannelInboundHandler<MessageVo> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageVo msg) throws Exception {
        MessageHead head = msg.getHead();
        byte type = head.getType();
        if (type == MessageType.HEARTBEAT_REQ.value()) {
            System.out.println("收到心跳请求");
            System.out.println(msg);
            MessageVo messageVo = buildHeartbeatMessage();
            ctx.writeAndFlush(messageVo);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private MessageVo buildHeartbeatMessage() {
        MessageVo msg = new MessageVo();
        MessageHead head = new MessageHead();
        head.setType(MessageType.HEARTBEAT_RESP.value());
        msg.setHead(head);
        msg.setBody("SUCCESS");
        return msg;
    }
}
