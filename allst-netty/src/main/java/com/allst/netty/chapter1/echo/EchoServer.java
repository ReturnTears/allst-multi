package com.allst.netty.chapter1.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author YiYa
 * @since 2020-04-18 下午 08:51
 */
public class EchoServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        final EchoServerHandler serverHandler = new EchoServerHandler();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.handler(new LoggingHandler(LogLevel.INFO));
            b.childHandler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel channel) {
                    ChannelPipeline p = channel.pipeline();
                    p.addLast(new LoggingHandler(LogLevel.INFO));
                    p.addLast(serverHandler);
                }
            });
            ChannelFuture cf = b.bind(8090).sync();
            cf.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }
}
