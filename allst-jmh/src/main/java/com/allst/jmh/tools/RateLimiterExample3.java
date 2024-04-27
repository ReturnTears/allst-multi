package com.allst.jmh.tools;

import com.google.common.util.concurrent.RateLimiter;

import static java.lang.Thread.currentThread;

/**
 * @author Hutu
 * @since 2024-04-27 下午 09:25
 */
public class RateLimiterExample3 {
    // 定义单位时间（1秒）的速率或者可用的许可证数量
    private static final RateLimiter rateLimiter = RateLimiter.create(2.0d);

    /**
     * 运行结果：
     * 0.0
     * 1.996957
     * 0.997505
     * 1.499299
     *
     * 第二次调用rateLimiter.acquire(2)方法时耗时为2秒，原因就是因为第一次的透支。
     */
    public static void main(String[] args) {
        //第一次就申请4个这样会透支下一次请求的时间
        System.out.println(rateLimiter.acquire(4));
        System.out.println(rateLimiter.acquire(2));
        System.out.println(rateLimiter.acquire(3));
        System.out.println(rateLimiter.acquire(2));
    }
}
