package com.netty.extend.server;

import com.netty.extend.serialize.kryo.KryoDecoder;
import com.netty.extend.serialize.kryo.KryoEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 粘包拆包
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024,0,2,0,2));
        pipeline.addLast(new LengthFieldPrepender(2));// 增加消息体长度

        // 解码  Byte --> MessageVo
        pipeline.addLast(new KryoDecoder());
        // 编码  MessageVo --> Byte
        pipeline.addLast(new KryoEncoder());

        // 心跳
        pipeline.addLast(new HeartbeatRespHandler());

        // 业务处理
        pipeline.addLast(new ServerHandler());

    }
}
