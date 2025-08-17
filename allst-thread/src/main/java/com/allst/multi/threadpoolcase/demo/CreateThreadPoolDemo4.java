package com.allst.multi.threadpoolcase.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 可调度线程池
 *
 * @author Hutu
 * @since 2025-08-17 下午 05:28
 */
public class CreateThreadPoolDemo4 {
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
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(2);
        for (int i = 0; i < 2; i++) {
            // 0表示首次执行任务的延迟时间，500表示每次执行任务的间隔时间
            scheduled.scheduleAtFixedRate(new TargetTask(), 0, 500, TimeUnit.MILLISECONDS);
        }
        Thread.sleep(1000);
        scheduled.shutdown();
    }
}
