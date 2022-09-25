package com.chatroom.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.apache.rocketmq.logging.Slf4jLoggerFactory;

import java.util.Scanner;

public class RoomClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("encoder",new StringEncoder());
                            pipeline.addLast("decoder",new StringDecoder());
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });
            ChannelFuture cf = bootstrap.connect("localhost", 9000).sync();
            while (true){
                Scanner scan = new Scanner(System.in);
                // 从控制台读取一行数据，返回值字符串
                String str = scan.nextLine();
                cf.channel().writeAndFlush(str);
            }

//            cf.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }

    }
}
