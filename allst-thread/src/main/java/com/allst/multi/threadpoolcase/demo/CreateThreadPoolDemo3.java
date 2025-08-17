package com.allst.multi.threadpoolcase.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 可缓存线程案例
 *
 * @author Hutu
 * @since 2025-08-17 下午 05:04
 */
public class CreateThreadPoolDemo3 {
    public static final int SLEEP_GAP = 500;

    static class TargetTask implements Runnable {
        static AtomicInteger taskNo = new AtomicInteger();
        private final String taskName;

        public TargetTask() {
            taskName = "task-" + taskNo.get();
            taskNo.incrementAndGet();
        }

        @Override
        public void run() {
            System.out.println("task : " + taskName + " doing");
            try {
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(taskName + " running over");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            pool.execute(new CreateThreadPoolDemo.TargetTask());
            pool.submit(new CreateThreadPoolDemo.TargetTask());
        }
        Thread.sleep(1000);
        // 关闭线程池
        pool.shutdown();
    }
}
