package com.allst.jmh.tools;

import java.util.Date;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * 将Phaser当作CountDownLatch来使用
 *
 * @author Hutu
 * @since 2024-04-24 下午 09:56
 */
public class PhaserExample1 {
    public static void main(String[] args) throws InterruptedException {
        // 定义一个Phaser，并未指定分片数量parties，此时在Phaser内部分片的数量parties默认为0，后面可以通过register方法动态增加
        final Phaser phaser = new Phaser();
        // 定义10个线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // 首先调用phaser的register方法使得phaser内部的parties加一
                phaser.register();
                try {
                    // 采取随机休眠的方式模拟线程的运行时间开销
                    TimeUnit.SECONDS.sleep(current().nextInt(20));
                    // 线程任务结束，执行arrive方法
                    phaser.arrive();
                    System.out.println(new Date() + ":" + currentThread() + " completed the work.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "T-" + i).start();
        }
        TimeUnit.SECONDS.sleep(10);
        // 主线程也调用注册方法，此时parties的数量为11=10+1
        phaser.register();
        // 主线程也arrive，但是它要等待下一个阶段，等待下一个阶段的前提是所有的线程都arrive，也就是phaser内部当前phase的unarrived数量为0
        phaser.arriveAndAwaitAdvance();
        // 通过下面的assertion就可以断言我们上面的判断
        assert phaser.getRegisteredParties() == 11 : "total 11 parties is registered.";
        System.out.println(new Date() + ": all of sub task completed work.");
    }
}
