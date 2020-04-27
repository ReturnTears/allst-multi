package com.allst.netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 一个buffer完成文件的读写
 * @author YiYa
 * @since 2020-04-27 下午 10:11
 */
public class NioFileChannel3 {
    public static void main(String[] args) throws Exception {
        File file = new File("E:\\IdeaProjects\\allst-multi\\allst-netty\\src\\main\\resources\\static.file\\1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel inChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("E:\\IdeaProjects\\allst-multi\\allst-netty\\src\\main\\resources\\static.file\\2.txt");
        FileChannel outChannel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            buffer.clear();
            int read = inChannel.read(buffer);
            if (read == -1) {
                break;
            }
            // 反转buffer
            buffer.flip();
            outChannel.write(buffer);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}
