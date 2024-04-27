package com.allst.jmh.tools;

import com.google.common.util.concurrent.RateLimiter;

import static java.lang.Thread.currentThread;

/**
 * @author Hutu
 * @since 2024-04-27 下午 09:25
 */
public class RateLimiterExample1 {
    // 定义一个Rate Limiter，单位时间（默认为秒）的设置为0.5
    private static final RateLimiter rateLimiter = RateLimiter.create(0.5);

    public static void main(String[] args) {
        while (true) {
            testRateLimiter();
        }
    }

    // 测试 rate limiter
    private static void testRateLimiter() {
        // 在访问该方法之前首先要进行rateLimiter的获取，返回值为实际的获取等待开销时间
        double elapsedSecond = rateLimiter.acquire();
        System.out.println(currentThread() + ": elapsed seconds: " + elapsedSecond);
    }
}
