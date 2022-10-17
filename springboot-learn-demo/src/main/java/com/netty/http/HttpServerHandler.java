package com.netty.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netty.utils.ApiSpace;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public class HttpServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("aaaaaaaaaaaaaaaaaaaaa: " + msg.getClass().toString());
        FullHttpRequest request = (FullHttpRequest) msg;
        String uri = request.uri();
        HttpMethod method = request.method();
        System.out.println("uri: " + uri);

        ByteBuf content = request.content();

        // todo 根据请求类型进行分发
        if (HttpMethod.GET.equals(method)) {
            // todo   业务处理数据 content

            if("/test".equals(uri)) {
                JSONObject jsonObject = ApiSpace.mingRenMingYan(1, JSONObject.class);
                String context = parseData(jsonObject);
                System.out.println(context);
//            ctx.writeAndFlush(context);
                sendResponse(ctx, HttpResponseStatus.OK, context);
            }
        } else {
            String context = "error";
            sendResponse(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR, context);
        }

    }

    private void sendResponse(ChannelHandlerContext ctx, HttpResponseStatus status, String context) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(context.getBytes());
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, byteBuf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private String parseData(JSONObject jsonObject) {
        int code = jsonObject.getIntValue("code");
        if (code == 200) {
            JSONArray data = jsonObject.getJSONArray("data");
            if (!CollectionUtils.isEmpty(data)) {
                JSONObject object = data.getJSONObject(data.size() - 1);
                String text = object.getString("text");
                String comefrom = object.getString("comefrom");
                String category = object.getString("category");
                StringBuilder sb = new StringBuilder(text);
                if (StringUtils.isNoneBlank(comefrom)) {
                    sb.append("——").append(comefrom);
                }
                if (StringUtils.isNoneBlank(category)) {
                    sb.append("《").append(category).append("》");
                }
                return sb.toString();
            }
        }
        return jsonObject.getString("message");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.toString());
        super.exceptionCaught(ctx, cause);
        ctx.channel().close();
    }
}
