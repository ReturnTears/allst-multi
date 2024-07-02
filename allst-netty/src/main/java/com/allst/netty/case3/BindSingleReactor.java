package com.allst.netty.case3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Hutu
 * @since 2024-07-02 下午 10:36
 */
public class BindSingleReactor {

    public void bindSingleReactor(int port) {
        EventLoopGroup reactorGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
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
