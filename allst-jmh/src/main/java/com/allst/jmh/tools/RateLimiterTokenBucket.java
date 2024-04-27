package com.allst.jmh.tools;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 令牌环桶算法
 *
 * @author Hutu
 * @since 2024-04-27 下午 10:05
 */
public class RateLimiterTokenBucket {
    // 当前活动商品数量
    private final static int MAX = 1000;
    // 订单编号，订单成功之后会产生一个新的订单
    private int orderID;
    // 单位时间内只允许10个用户能够抢购到商品，也就是说订单服务将会被匀速地调用
    private final RateLimiter rateLimiter = RateLimiter.create(10.0D);
    private final Monitor bookOrderMonitor = new Monitor();

    // 当前商品售罄的时候就会抛出该异常
    static class NoProductionException extends Exception {
        public NoProductionException(String message) {
            super(message);
        }
    }

    // 当抢购商品失败时就会抛出该异常
    static class OrderFailedException extends Exception {
        public OrderFailedException(String message) {
            super(message);
        }
    }

    // 前台用户下订单，但是只允许匀速地进行订单服务调用
    public void bookOrder(Consumer<Integer> consumer) throws NoProductionException, OrderFailedException {
        // 如果当前商品有库存则执行抢购操作
        if (bookOrderMonitor.enterIf(bookOrderMonitor.newGuard(() -> orderID < MAX))) {
            try {
                // 抢购商品，最多等待100毫秒
                if (!rateLimiter.tryAcquire(100, TimeUnit.MILLISECONDS)) {
                    // 如果在100毫秒之内抢购仍然失败，则抛出订购失败的异常，客户端可以尝试重试操作
                    throw new OrderFailedException("book order failed, please try again later.");
                }
                // 执行订单订购操作
                orderID++;
                consumer.accept(orderID);
            } finally {
                bookOrderMonitor.leave();
            }
        } else {
            // 如果当前商品已经没有库存，则抛出没有商品的异常，该异常将不会再次进行尝试动作
            throw new NoProductionException("No available production now.");
        }
    }
}
