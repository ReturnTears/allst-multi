package com.allst.async.chapter2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 在Java中我们可以使用线程池来实现线程复用，每当我们需要执行异步任务时，可以把任务投递到线程池里进行异步执行。
 *
 * @author Hutu
 * @since 2024-01-06 下午 09:52
 */
@Slf4j
public class AsyncThreadPool3 {
    // 0自定义线程池
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1,
            TimeUnit.MINUTES, new LinkedBlockingQueue<>(5),
            new NamedThreadFactory("async-pool"),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    private static String handleAndResult() {
        log.info("Come in method start.");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Come in method end.");
        return "task has done";
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 1.开启异步单元执行任务A
        Future<?> resultA = POOL_EXECUTOR.submit(() -> {
            String name = Thread.currentThread().getName();
            log.info("async thread name : {}", name);
            return handleAndResult();
        });
        log.info("main method can do other things");
        // 2.同步等待执行结果
        log.info("exec result : {}", resultA.get());
    }
}
