package com.allst.netty.nio;

import java.nio.IntBuffer;

/**
 * Buffer
 *
 * @author YiYa
 * @since 2020-04-15 下午 11:08
 */
public class BasicBuffer {
    public static void main(String[] args) {
        // 创建一个Buffer对象，大小为5，用于存放int类型数据
        IntBuffer intBuffer = IntBuffer.allocate(5);
        // 存放元素
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put((i + 1) * 2);
        }
        // 读取元素
        intBuffer.flip();   // 读写切换
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
