package com.netty.extend.serialize.kryo;

import com.netty.extend.vo.MessageVo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class KryoEncoder extends MessageToByteEncoder<MessageVo> {


    @Override
    protected void encode(ChannelHandlerContext ctx, MessageVo msg, ByteBuf out) throws Exception {
        byte[] bytes = KryoSerializer.serialize(msg);
        out.writeBytes(bytes);
    }
}
