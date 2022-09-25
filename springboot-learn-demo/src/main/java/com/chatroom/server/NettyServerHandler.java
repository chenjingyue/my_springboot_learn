package com.chatroom.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
    public static List<Channel> channelList = new ArrayList<>();


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        for (Channel channel1 : channelList) {
            if (channel != channel1) {
                channel1.writeAndFlush("[" + channel.remoteAddress().toString() + "]: 上线了");
            }
        }
        channelList.add(channel);

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel channel1 : channelList) {
            if (channel != channel1) {
                channel1.writeAndFlush("[" + channel.remoteAddress().toString() + "]: 下线了");
            }
        }
        channelList.remove(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        Channel channel = ctx.channel();
//
//        for (Channel channel1 : channelList) {
//            channel1.writeAndFlush("[" + channel.remoteAddress().toString() + "]: 上线了");
//        }
//        channelList.add(channel);

    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        for (Channel channel1 : channelList) {
            if (channel != channel1) {
                channel1.writeAndFlush("[" + channel.remoteAddress().toString() + "]: " + msg);
            } else {
                channel1.writeAndFlush("[自己]: " + msg);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ByteBuf buf = Unpooled.copiedBuffer("已收到客户端消息", CharsetUtil.UTF_8);
//        ctx.writeAndFlush(buf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
