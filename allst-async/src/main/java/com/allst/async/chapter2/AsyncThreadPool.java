package com.allst.async.chapter2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 在Java中我们可以使用线程池来实现线程复用，每当我们需要执行异步任务时，可以把任务投递到线程池里进行异步执行。
 * <p>
 * 线程池的拒绝策略设置为CallerRunsPolicy，即当线程池任务饱和，执行拒绝策略时不会丢弃新的任务，而是会使用调用线程来执行
 *
 * @author Hutu
 * @since 2024-01-06 下午 09:52
 */
public class AsyncThreadPool {
    // 0自定义线程池
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1,
            TimeUnit.MINUTES, new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

    private static void handle1() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Come in handle1 method");
    }

    private static void handle2() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Come in handle2 method");
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        // 1．开启异步单元执行任务A
        POOL_EXECUTOR.execute(() -> {
            try {
                handle1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // 2．执行任务B
        handle2();
        // 3．同步等待线程A运行结束
        System.out.println(System.currentTimeMillis() - start);
        // 4．挂起当前线程
        Thread.currentThread().join();
    }
}
