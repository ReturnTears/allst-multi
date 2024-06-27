package com.allst.netty.case1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author Hutu
 * @since 2024-06-26 下午 10:43
 */
public final class NettyFirstServer {
    static final int port = Integer.parseInt(System.getProperty("port", "8686"));

    public static void main(String[] args) {
        /*
        NioEventLoopGroup对象是⼀个基于Reactor模式的线程池，⽤于绑定在ServerBootstrap对象上
        本例程实现了⼀个服务端应⽤，因此创建了两个NioEventLoopGroup对象（bossGroup和workerGroup），
        第⼀个对象（bossGroup）⽤来处理客⼾端的连接，
        第⼆个对象（workerGroup）⽤来处理连接后的I/O读写请求以及系统任务。
        */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            /*
            ServerBootstrap是⼀个启动NIO服务的辅助类，核⼼功能就是⽤于服务器的初始化。
             */
            ServerBootstrap b = new ServerBootstrap();
            // ServerBootstrap对象（b）调⽤了⼀个函数链（⼀组连续被调⽤的函数）
            b.group(bossGroup, workerGroup)
                // 将指定NioServerSocketChannel类绑定到服务器端的Channel上
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new NettyFirstServerHandler());
                    }
                });
            // 通过sync()⽅法定义启动⽅式为同步⽅式
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
