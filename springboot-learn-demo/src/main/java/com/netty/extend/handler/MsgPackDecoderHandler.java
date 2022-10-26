package com.netty.extend.handler;

import com.netty.extend.vo.MessageVo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

public class MsgPackDecoderHandler extends ByteToMessageDecoder {


    /**
     * 版本  org.msgpack  msgpack  0.6.12
     * 问题： Object 无法序列化
     *  Cannot find template for class java.lang.Object class. Try to add @message annotation to the class or call MessagePack.register(Type).
     *
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("------------------");
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        MessagePack pack = new MessagePack();
        MessageVo message = pack.read(bytes, MessageVo.class);
        out.add(message);
    }
}
