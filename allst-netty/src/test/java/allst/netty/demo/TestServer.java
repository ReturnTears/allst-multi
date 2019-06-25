package allst.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 测试服务类
 * @Auther JUNN
 * @Date 2019-06-23 下午 11:45
 */
public class TestServer {

    public static void main(String[] args) throws Exception {
        /**
         * 定义两个事件循环线程组
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();     // 到客户端接收连接，不对连接做任何处理，将接收到的连接转给workerGroup
        EventLoopGroup workerGroup = new NioEventLoopGroup();   // 这个连接来对后续的处理，将结果返回给客户端

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
