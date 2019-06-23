package allst.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * Http处理器
 * @Auther JUNN
 * @Date 2019-06-23 下午 11:57
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * 读取客户端发过来的真正的请求，并且向客户端响应的这样一个方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            // 设置消息头
            /*response.headers().set(HttpHeadersNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeadersNames.CONTENT_LENGTH, content.readableBytes());*/

            ctx.writeAndFlush(response);
        }
    }
}
