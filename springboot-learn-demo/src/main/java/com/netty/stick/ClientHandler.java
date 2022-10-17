package com.netty.stick;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("channelRead--> " + new String(byteBuf.array()));
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String str = "知识可羡，胜于财富——佚名《关于知识的经典语录》";
        int count = 100;
        for (int i = 0; i < count; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer((str + i + "@").getBytes());
            ctx.writeAndFlush(byteBuf);
        }
        System.out.println("发送完成！！！");
        ctx.close();
        System.out.println("断开连接！！！");
    }
}
