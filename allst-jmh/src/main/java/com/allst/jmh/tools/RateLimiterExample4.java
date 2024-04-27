package com.allst.jmh.tools;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Hutu
 * @since 2024-04-27 下午 10:01
 */
public class RateLimiterExample4 {

    private static final AtomicInteger data = new AtomicInteger(0);
    private static final RateLimiterBucket bucket = new RateLimiterBucket();

    public static void main(String[] args) {
        // 启动10个线程模拟高并发的业务请求
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    bucket.submitRequest(data.getAndIncrement());
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        // 启动10个线程模拟匀速地对漏桶中的请求进行处理
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    bucket.handleRequest(System.out::println);
                }
            }).start();
        }
    }
}
