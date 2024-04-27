package com.allst.jmh.tools;

import static java.lang.Thread.currentThread;

/**
 * @author Hutu
 * @since 2024-04-27 下午 10:08
 */
public class RateLimiterExample5 {
    private static final RateLimiterTokenBucket tokenBucket = new RateLimiterTokenBucket();

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() ->
            {
                while (true) {
                    try {
                        // 抢购商品
                        tokenBucket.bookOrder(prodID -> System.out.println("User: " + currentThread() + " book the prod order and prodID:" + prodID));
                    } catch (RateLimiterTokenBucket.NoProductionException e) {
                        // 当前商品已经售罄，退出抢购
                        System.out.println("all of production already sold out.");
                        break;
                    } catch (RateLimiterTokenBucket.OrderFailedException e) {
                        // 抢购失败，然后尝试重新抢购
                        System.out.println("User: " + currentThread() + " book order failed, will try again.");
                    }
                }
            }).start();
        }
    }
}
