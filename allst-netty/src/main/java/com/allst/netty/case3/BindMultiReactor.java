package com.allst.netty.case3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty 多线程模型
 *
 * @author Hutu
 * @since 2024-07-12 下午 10:13
 */
public class BindMultiReactor {

    public void bindMultiReactor(int port) {
        EventLoopGroup acceptToGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        try {
            // 定义了初始化分发器对象的实例serverBootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // group方法两个参数定义了两个实例，标识这是一个多线程Netty模型应用
            serverBootstrap.group(acceptToGroup, nioEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast();
                        }
                    });
            Channel channel = serverBootstrap.bind(port).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            acceptToGroup.shutdownGracefully();
            nioEventLoopGroup.shutdownGracefully();
        }
    }

}

