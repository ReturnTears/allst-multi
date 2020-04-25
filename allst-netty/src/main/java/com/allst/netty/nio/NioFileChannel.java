package com.allst.netty.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author YiYa
 * @since 2020-04-25 下午 11:30
 */
public class NioFileChannel {
    public static void main(String[] args) throws Exception {
        final String str = "Hello Netty~";
        final String path = "E:\\TestData\\netty.txt";
        // 创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        // 创建FileChannel
        FileChannel fileChannel = fileOutputStream.getChannel();
        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 往缓冲区写入数据
        byteBuffer.put(str.getBytes());
        // 反转, 即是将position一定到最初的位置
        byteBuffer.flip();
        // 将缓冲区的内容写入到FileChannel中
        fileChannel.write(byteBuffer);
        // 关闭输出流
        fileOutputStream.close();
    }
}
