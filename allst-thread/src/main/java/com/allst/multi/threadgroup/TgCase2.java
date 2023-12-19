package com.allst.multi.threadgroup;

/**
 * @author Hutu
 * @since 2023-12-19 下午 10:49
 */
public class TgCase2 {
    public static void main(String[] args) {
        ThreadGroup threadGroup1 = new ThreadGroup("my-1");
        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1, "my-2");
        Thread thread = new Thread(threadGroup2, "Kang");
        thread.start();
        System.out.println(threadGroup2.activeCount());
    }
}
