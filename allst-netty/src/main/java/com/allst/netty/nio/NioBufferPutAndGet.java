package com.allst.netty.nio;

import java.nio.ByteBuffer;

/**
 * Buffer的put和get使用注意事项：
 * buffer支持类型化方式put和get数据
 *
 * @author YiYa
 * @since 2020-04-27 下午 10:54
 */
public class NioBufferPutAndGet {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(77);
        byteBuffer.putChar('H');
        byteBuffer.putInt(77);
        byteBuffer.putLong(12L);
        byteBuffer.putDouble(12.34);
        byteBuffer.put(new byte[] {1});
        // 反转
        byteBuffer.flip();

        System.out.println("🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎🍎");
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.getChar());   // put和get类型化方式不一致会报错：java.nio.BufferUnderflowException
    }
}
