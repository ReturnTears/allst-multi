package allst.netty.exam2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * 定义自己的处理器
 * @Auther JUNN
 * @Date 2019-07-15 上午 03:25
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * channelRead0
     * @param ctx
     *              可以获取上下文
     * @param msg
     *              客户端发过来的请求对象
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "," + msg);
        ctx.channel().writeAndFlush("from server : " + UUID.randomUUID());
    }

    /**
     * 异常处理方法
     * @param ctx
     *                  上下文
     * @param cause
     *                  异常
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
