package com.netty.extend.serialize.kryo;

import com.netty.extend.vo.MessageVo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class KryoDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readableBytes();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        MessageVo messageVo = KryoSerializer.deserialize(bytes, MessageVo.class);
        out.add(messageVo);
    }

}
