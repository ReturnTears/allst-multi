package com.allst.multi.threadpoolcase.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 单线程化线程池案例
 *
 * @author Hutu
 * @since 2025-08-16 下午 05:40
 */
public class CreateThreadPoolDemo {
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
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            pool.execute(new TargetTask());
            pool.submit(new TargetTask());
        }
        Thread.sleep(1000);
        // 关闭线程池
        pool.shutdown();
    }
}
