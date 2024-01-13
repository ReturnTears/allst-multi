package com.allst.async.chapter3;

import java.util.concurrent.*;

/**
 * CompletableFuture是一种可以通过编程显式设置结果的future
 *
 * @author Hutu
 * @since 2024-01-13 下午 09:05
 */
public class CompletableFutureCode {

    private final static int AVALIABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVALIABLE_PROCESSORS, AVALIABLE_PROCESSORS * 2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1.创建一个CompletableFuture对象
        CompletableFuture<String> future = new CompletableFuture<>();
        // 2.开启线程计算任务结果，并设置
        POOL_EXECUTOR.execute(() -> {
            try {
                // 2.1休眠3s，模拟任务计算
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 2.2设置计算结果到future
            System.out.println("-----" + Thread.currentThread().getName() + " set future result ------");
            future.complete("hello, KanKan ");
        });
        // 3.等待计算结果
        System.out.println("---main thread wait future result---");
        System.out.println(future.get());
        // System.out.println(future.get(1000,TimeUnit.MILLISECONDS));
        System.out.println("---main thread got future result---");
    }
}
