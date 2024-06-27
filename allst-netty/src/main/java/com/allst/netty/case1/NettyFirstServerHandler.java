package com.allst.netty.case1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Hutu
 * @since 2024-06-26 下午 11:16
 */
public class NettyFirstServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 每当从客⼾端接收到新数据信息时，这个⽅法会在收到数据信息时被调⽤。
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("read data is : " + msg);
        /*
        处理器通过release()⽅法忽略所有接收到的消息。
        另外，当收到的数据信息是⼀个ByteBuf时（⼀个引⽤计数对象），那么这个对象就必须显式地调⽤release()⽅法来释放。
         */
        ((ByteBuf)msg).release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 出现异常时关闭链接
        cause.printStackTrace();
        ctx.close();
    }
}
