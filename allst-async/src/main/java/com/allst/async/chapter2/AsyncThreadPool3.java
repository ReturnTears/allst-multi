package com.allst.async.chapter2;

import java.util.concurrent.*;

/**
 * 在Java中我们可以使用线程池来实现线程复用，每当我们需要执行异步任务时，可以把任务投递到线程池里进行异步执行。
 *
 * @author Hutu
 * @since 2024-01-06 下午 09:52
 */
public class AsyncThreadPool3 {
    // 0自定义线程池
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1,
            TimeUnit.MINUTES, new LinkedBlockingQueue<>(5),
            new NamedThreadFactory("async-pool"),
            new ThreadPoolExecutor.CallerRunsPolicy());

    private static String handleAndResult() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Come in handle1 method");
        return "task has done";
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 1.开启异步单元执行任务A
        Future<?> resultA = POOL_EXECUTOR.submit(() -> handleAndResult());
        // 2.同步等待执行结果
        System.out.println(resultA.get());
    }
}
