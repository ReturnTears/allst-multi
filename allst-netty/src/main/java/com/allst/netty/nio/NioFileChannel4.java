package com.allst.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * transferFrom
 * @author YiYa
 * @since 2020-04-27 下午 10:41
 */
public class NioFileChannel4 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("E:\\TestData\\gc.log");
        FileChannel inChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("E:\\TestData\\gc-backup.log");
        FileChannel outChannel = fileOutputStream.getChannel();
        // transferFrom完成拷贝
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        // 关闭流和通道
        inChannel.close();
        outChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
