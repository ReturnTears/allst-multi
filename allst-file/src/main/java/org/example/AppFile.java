package org.example;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Hutu
 * @since 2024-09-13 下午 09:34
 */
public class AppFile {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        try (RandomAccessFile raf = new RandomAccessFile("E:\\TestData\\file\\inp.txt", "rw")) {
            MappedByteBuffer mappedByteBuffer = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, raf.length());
            boolean loaded = mappedByteBuffer.isLoaded();
            if (loaded) {
                // 获取当前读写位置
                int position = mappedByteBuffer.position();
                System.out.println(position);
            }
        } finally {
            System.out.println("finally");
        }
    }
}