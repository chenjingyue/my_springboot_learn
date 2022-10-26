package com.netty.extend.handler;

import com.netty.extend.vo.MessageVo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.msgpack.MessagePack;

import java.util.List;

public class MsgPackEncoderHandler extends MessageToByteEncoder<MessageVo> {


    @Override
    protected void encode(ChannelHandlerContext ctx, MessageVo msg, ByteBuf out) throws Exception {
        System.out.println("------------MsgPackEncoderHandler-------------");
        MessagePack pack = new MessagePack();
        byte[] bytes = pack.write(msg);
        out.writeBytes(bytes);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
