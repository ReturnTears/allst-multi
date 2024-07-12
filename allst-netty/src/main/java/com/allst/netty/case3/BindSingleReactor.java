package com.allst.netty.case3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty 单线程模型
 * @author Hutu
 * @since 2024-07-02 下午 10:36
 */
public class BindSingleReactor {

    public void bindSingleReactor(int port) {
        // 定义了一个单线程实例reactorGroup
        EventLoopGroup reactorGroup = new NioEventLoopGroup();
        try {
            // 定义了初始化分发器对象的实例serverBootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 调用一组相应的方法来初始化事件处理器，group方法两个参数定义了同一个实例，标识这是一个单线程Netty模型应用
            serverBootstrap.group(reactorGroup, reactorGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast();
                    }
                });
            Channel channel = serverBootstrap.bind(port).sync().channel();
            channel.close().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            reactorGroup.shutdownGracefully();
        }
    }
}
