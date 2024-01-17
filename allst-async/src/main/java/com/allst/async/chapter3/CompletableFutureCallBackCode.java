package com.allst.async.chapter3;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * CompletableFuture是一种可以通过编程显式设置结果的future
 *
 * @author Hutu
 * @since 2024-01-13 下午 09:05
 */
public class CompletableFutureCallBackCode {

    private final static int AVALIABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVALIABLE_PROCESSORS, AVALIABLE_PROCESSORS * 2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // thenApply();
        whenComplete();
        System.out.println("this is main method");
    }

    // 基于thenApply实现异步任务
    private static void thenApply() throws ExecutionException, InterruptedException {
        // 1.创建异步任务，并返回future
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                // 休眠2s,模拟任务计算
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 返回计算结果
                return "one future calc result";
            }
        });

        // 2.在future上添加事件，当future计算完成后回调该事件，并返回新future
        CompletableFuture<String> twoFuture = oneFuture.thenApply(new Function<String, String>() {
            // 在步骤1的基础上进行计算，这里s为步骤1返回的结果
            @Override
            public String apply(String s) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return s + " , this two future calc result";
            }
        });

        // 同步等再twoFuture对应的任务完成， 并获取结果
        System.out.println(twoFuture.get());

        System.out.println("---main thread got future result---");
    }

    // 基于whenComplete设置回调函数
    private static void whenComplete() throws InterruptedException {
        // 创建一个CompletableFuture对象
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                // 模拟异步任务执行
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 返回计算结果
                return "hello, one future";
            }
        });
        // 添加回调函数
        oneFuture.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable u) {
                // 没有异常，打印同步任务结果
                if (null == u) {
                    System.out.println("异步回调结果：" + s);
                } else {
                    // 打印异常信息
                    System.out.println(u.getLocalizedMessage());
                }
            }
        });

        System.out.println("this is main thread : " + Thread.currentThread().getName());

        // 挂起当前线程，等待异步任务执行完毕
        Thread.currentThread().join();
    }
}
