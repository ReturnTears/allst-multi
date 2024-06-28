package com.allst.netty.case2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Hutu
 * @since 2024-06-28 下午 09:30
 */
public class EchoNettyClientHandler extends ChannelInboundHandlerAdapter {
    private final ByteBuf firstMessage;
    public EchoNettyClientHandler() {
        firstMessage = Unpooled.buffer(256);
        for (int i = 0; i < firstMessage.capacity(); i++) {
            firstMessage.writeByte((byte) i);
        }
    }
    public void channelActive(io.netty.channel.ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(firstMessage);
    }
    public void channelRead(io.netty.channel.ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server return msg is : " + msg);
        ctx.channel().close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public void exceptionCaught(io.netty.channel.ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
