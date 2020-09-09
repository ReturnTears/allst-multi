package com.allst.netty.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer
 * 可以让文件直接子内存中修改， 操作系统不需要拷贝
 * @author YiYa
 * @since 2020-09-09 下午 11:10
 */
public class NioMappedByteBuffer {
    public static void main(String[] args) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("nio.txt", "rw");
            // 获取对应的通道
            FileChannel fileChannel = randomAccessFile.getChannel();
            /**
             * 参数介绍：
             * 1、操作模式
             * 2、直接修改的起始位置
             * 3、映射到内存的字节大小
             */
            MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 10);
            byteBuffer.put(0, (byte) 'h').put(7, (byte) 'a');
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("修改文件成功~");
        }
    }
}
