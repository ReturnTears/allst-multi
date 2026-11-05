package com.allst.async.chapter2;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 在Java中我们可以使用线程池来实现线程复用，每当我们需要执行异步任务时，可以把任务投递到线程池里进行异步执行。
 * 为了进一步释放main线程的负担，也就是可以把任务handle2的执行也提交到线程池内进行异步执行
 *
 * @author Hutu
 * @since 2024-01-06 下午 09:52
 */
@Slf4j
public class AsyncThreadPool2 {
    // 0自定义线程池
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            10,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(5),
            new NamedThreadFactory("async-pool"),          // 复用同包已有的工厂
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    private static void handle1() {
        log.info("handle1 start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("handle1 end");
    }

    private static void handle2() {
        log.info("handle2 start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("handle2 end");
    }

    public static void main(String[] args) throws InterruptedException {
        POOL_EXECUTOR.allowCoreThreadTimeOut(true);
        long start = System.currentTimeMillis();
        // 1．开启异步单元执行任务A
        CompletableFuture<Void> taskA = submit("handle1", AsyncThreadPool2::sleepTask);
        CompletableFuture<Void> taskB = submit("handle2", AsyncThreadPool2::sleepTask);
        // 2. 等两个任务都结束再统计，否则这里打印的是 0ms
        CompletableFuture.allOf(taskA, taskB).join();
        // 3．同步等待线程A运行结束
        log.info("execute time:{}", System.currentTimeMillis() - start);
        // 4．挂起当前线程
        // Thread.currentThread().join();

        // 任务数量再涨时，把 main 换成列表驱动即可，不用再复制任何代码块
        /*List<String> taskNames = Lists.newArrayList("handle1", "handle2", "handle3");
        CompletableFuture<?>[] futures = taskNames.stream()
                .map(name -> submit(name, AsyncThreadPool2::sleepTask))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();*/
    }

    /**
     * 与 Runnable 的唯一区别：允许抛受检异常。
     * 这样异常兜底能统一收到提交模板里，lambda 内部不必再写 try/catch。
     */
    @FunctionalInterface
    private interface Task {
        void run() throws Exception;
    }

    /** 提交模板：日志、计时、异常处理只写一次 */
    private static CompletableFuture<Void> submit(String name, Task task) {
        return CompletableFuture.runAsync(() -> {
            long begin = System.currentTimeMillis();
            log.info("{} start", name);
            try {
                task.run();
                log.info("{} end, cost {} ms", name, System.currentTimeMillis() - begin);
            } catch (Exception e) {
                log.error("{} failed", name, e);           // 替代 e.printStackTrace()
            }
        }, POOL_EXECUTOR);
    }

    /** 业务逻辑只保留差异，休眠时长可参数化 */
    private static void sleepTask() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
    }
}
