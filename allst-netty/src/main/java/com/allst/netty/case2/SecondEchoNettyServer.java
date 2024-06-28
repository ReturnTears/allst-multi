package com.allst.netty.case2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author Hutu
 * @since 2024-06-28 下午 09:23
 */
public class SecondEchoNettyServer {
    public static void main(String[] args) {
        /*
        创建两个NioEventLoopGroup，第 ⼀ 个 对 象（bossGroup）⽤来处理客⼾端的连接，
            第⼆个对象（workerGroup）⽤来处理连接后的I/O读写请求以及系统任务。
        NioEventLoopGroup⽤于描述⼀个处理I/O操作的多线程事件循环，绑定在ServerBootstrap对象上。
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        final EchoNettyServerHandler handler = new EchoNettyServerHandler();
        try {
            /*
            ServerBootstrap类是Netty提供的用于启动服务端的类，
            ServerBootstrap是⼀个启动NIO服务的辅助类，核⼼功能就是⽤于服务器的初始化。
             */
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    /*
                    将指定NioServerSocketChannel类绑定到服务器端的Channel上。
                     */
                .channel(NioServerSocketChannel.class)
                    /*
                    通过调⽤option()⽅法来定义ChannelOption参数。
                     */
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new StringDecoder());
                        p.addLast(new StringEncoder());
                        p.addLast(handler);
                    }
                });
            ChannelFuture f = b.bind(8686).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
