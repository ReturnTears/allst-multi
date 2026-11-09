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
        try (RandomAccessFile raf = new RandomAccessFile("D:\\TestData\\file\\inp.txt", "rw")) {
            // raf.length() 为 0 时，map() 会抛出 IllegalArgumentException，使用前需判空。
            if (raf.length() == 0) {
                return;
            }
            MappedByteBuffer mappedByteBuffer = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, raf.length());
            // 主动触发所有页面的缺页加载，阻塞直到完成
            mappedByteBuffer.load();
            // load() 之后再检查才有意义（但仍非100%可靠）
            boolean loaded = mappedByteBuffer.isLoaded();
            System.out.println("loaded : " + loaded);
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