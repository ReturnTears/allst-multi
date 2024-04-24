package com.allst.jmh.tools;

import java.util.Date;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Phaser onAdvance方法的使用场景之一
 * 在Phaser中，onAdvance方法是非常重要的，它在每一个Phase（阶段）中除了会在所有的分片都到达之后执行一次调用之外，
 * 更重要的是，它还会决定该Phaser是否被终止（当onAdvance方法的返回值为true时，则表明该Phaser将被终止，接下来将不能再使用）
 *
 * @author Hutu
 * @since 2024-04-24 下午 10:14
 */
public class PhaserExample3 {
    public static void main(String[] args) {
        // 使用我们自定义的Phaser，并且在构造时传入回调函数
        final Phaser phaser = new MyPhaser(() ->
                System.out.println(new Date() + ": all of sub task completed work.")
        );

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                phaser.register();
                try {
                    TimeUnit.SECONDS.sleep(current().nextInt(20));
                    phaser.arriveAndAwaitAdvance();
                    System.out.println(new Date() + ":" + currentThread() + " completed the work.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "T-" + i).start();
        }
    }

    // 继承Phaser
    private static class MyPhaser extends Phaser {
        private final Runnable runnable;

        // 在构造函数中传入Runnable接口作为回调函数使用
        private MyPhaser(Runnable runnable) {
            super();
            this.runnable = runnable;
        }

        // 重写onAdvance方法，当parties个任务都到达某个phase时该方法将被调用执行
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            this.runnable.run();
            return super.onAdvance(phase, registeredParties);
        }

    }
}
