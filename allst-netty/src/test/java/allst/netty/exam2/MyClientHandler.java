package allst.netty.exam2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @Auther JUNN
 * @Date 2019-07-15 上午 03:39
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 服务器向客户端建立通讯的时候调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client output : " + msg);
        ctx.writeAndFlush("from client : " + LocalDateTime.now());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
