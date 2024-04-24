package com.allst.jmh.tools;

import java.util.Date;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * @author Hutu
 * @since 2024-04-24 下午 10:05
 */
public class PhaserExample2 {
    public static void main(String[] args) throws InterruptedException {
        // 定义一个分片parties为0的Phaser
        final Phaser phaser = new Phaser();
        for (int i = 0; i < 10; i++) {
            // 启动10个线程
            new Thread(() -> {
                // 子线程调用注册方法，当10个子线程都执行了register，parties将为10
                phaser.register();
                try {
                    // 随机休眠
                    TimeUnit.SECONDS.sleep(current().nextInt(20));
                    // 线程执行完之后，调用arriveAndAwaitAdvance()方法,等待所有线程arrive，然后继续前行
                    phaser.arriveAndAwaitAdvance();
                    System.out.println(new Date() + ":" + currentThread() + " completed the work.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "T-" + i).start();
        }
        // 休眠以确保其他子线程顺利调用register方法
        TimeUnit.SECONDS.sleep(10);
        // 主线程调用register方法，此时phaser内部的parties为11
        phaser.register();
        phaser.arriveAndAwaitAdvance();
        assert phaser.getRegisteredParties() == 11 : "total 11 parties is registered.";
        System.out.println(new Date() + ": all of sub task completed work.");
    }
}
