package com.allst.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering和Gathering
 * Scattering将数据写入到buffer时，可以采用buffer数组，依次写入
 * Gathering从buffer读取数据时，可以采用buffer数组， 依次读取
 *
 * telnet localhost port
 * @author YiYa
 * @since 2020-09-09 下午 11:31
 */
public class NioMoreBuffer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(3344);
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        // 等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int msgLength = 8;
        while (true) {
            int byteRead = 0;
            while (byteRead < msgLength) {
                long l = socketChannel.read(byteBuffers);
                byteRead += l;
                Arrays.stream(byteBuffers).map(b -> b.position() + " ~ " + b.limit()).forEach(System.out::println);
            }
            // 将所有的buffer进行flip
            Arrays.stream(byteBuffers).forEach(Buffer::flip);
            // 将数据读出显示
            long byteWrite = 0;
            while (byteWrite < msgLength) {
                long l = socketChannel.write(byteBuffers);
                byteWrite += l;
            }
            // clear所有buffer
            Arrays.stream(byteBuffers).forEach(Buffer::clear);
            System.out.println(byteRead + " - " + byteWrite);
        }
    }
}
