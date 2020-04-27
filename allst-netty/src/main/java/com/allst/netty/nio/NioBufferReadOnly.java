package com.allst.netty.nio;

import java.nio.IntBuffer;

/**
 * buffer 只读
 * @author YiYa
 * @since 2020-04-27 下午 11:05
 */
public class NioBufferReadOnly {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(17);
        for (int i = 0, j = buffer.capacity(); i < j; i++) {
            buffer.put(i);
        }

        buffer.flip();

        // 只读
        IntBuffer intBuffer = buffer.asReadOnlyBuffer();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

        // 因为设置了只读， 这时候添加数据就会报错：java.nio.ReadOnlyBufferException
        intBuffer.put(18);
    }
}
