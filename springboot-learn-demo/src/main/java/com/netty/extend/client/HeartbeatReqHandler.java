package com.netty.extend.client;

import com.netty.extend.vo.MessageHead;
import com.netty.extend.vo.MessageType;
import com.netty.extend.vo.MessageVo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Queue;
import java.util.concurrent.*;

public class HeartbeatReqHandler extends SimpleChannelInboundHandler<MessageVo> {

//    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(2);
//    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
//            0L, TimeUnit.MILLISECONDS, queue);

    private ScheduledExecutorService executor = Executors
            .newScheduledThreadPool(1);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("建立连接成功，准备发送心跳");
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                MessageVo messageVo = buildHeartbeatMessage();
                System.out.println("发送心跳");
                ctx.writeAndFlush(messageVo);
            }
        }, 1, 3, TimeUnit.SECONDS);

        super.channelActive(ctx);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageVo msg) throws Exception {
        MessageHead head = msg.getHead();
        byte type = head.getType();
        if (type == MessageType.HEARTBEAT_RESP.value()) {
            System.out.println("收到心跳响应");
            System.out.println(msg);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private MessageVo buildHeartbeatMessage() {
        MessageVo msg = new MessageVo();
        MessageHead head = new MessageHead();
        head.setType(MessageType.HEARTBEAT_REQ.value());
        msg.setHead(head);
        return msg;
    }
}
