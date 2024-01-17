package com.allst.async.chapter3;

import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 基于CompletableFuture实现异步计算与结果转换
 * 1、基于runAsync系列方法实现无返回值的异步计算：当你想异步执行一个任务，并且不需要任务的执行结果时可以使用该方法，比如异步打日志，异步做消息通知等
 *
 *
 * @author Hutu
 * @since 2024-01-13 下午 09:30
 */
public class CompletableFutureRunAsyncCode {

    public static void runAsync() throws ExecutionException, InterruptedException {
        // 创建异步任务，并返回future
        CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 打印日志
                System.out.println("runAsync over");
            }
        });
        // 同步等待异步任务执行结束
        System.out.println(future.get());
    }

    // 创建线程池
    private static final ThreadPoolExecutor bizPoolExecutor = new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10));

    // 没有返回值的异步执行，异步任务由业务自己的线程池执行
    public static void runAsyncWithBizExecutor() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 打印日志
                System.out.println("runAsyncWithBizExecutor over");
            }
        }, bizPoolExecutor);
        // 同步等待异步任务执行结束
        System.out.println(future.get());
    }

    // 有返回值的异步执行
    public static void supplyAsync() throws ExecutionException, InterruptedException {
        // 创建异步任务，并返回future
        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    // 模拟任务计算时长
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 返回异步计算结果
                return "Hello supplyAsync";
            }
        });
        // 同步等待异步任务执行结束
        System.out.println(future.get());
    }

    public static void supplyAsyncWithBizExecutor() throws ExecutionException, InterruptedException {
        // 创建异步任务，并返回future
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                // 模拟任务计算时长
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 返回异步计算结果
            return "Hello supplyAsyncWithBizExecutor";
        }, bizPoolExecutor);
        // 同步等待异步任务执行结束
        System.out.println(future.get());
    }

    // thenRun 不能访问oneFuture的结果
    public static void thenRun() throws ExecutionException, InterruptedException {
        // 创建异步任务，并返回future
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    // 模拟任务计算时长
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 返回异步计算结果
                return "Hello oneFuture";
            }
        });

        CompletableFuture<Void> twoFuture = oneFuture.thenRun(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("oneFuture result : " +oneFuture.get());
                    Thread.sleep(1000);
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName());
                System.out.println("--- after oneFuture over doSomething ---");
            }
        });
        System.out.println(twoFuture.get());
    }

    public static void thenAccept() throws ExecutionException, InterruptedException {
        // 创建异步任务，并返回future
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    // 模拟任务计算时长
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 返回异步计算结果
                return "Hello oneFuture";
            }
        });
        CompletableFuture<Void> twoFuture = oneFuture.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                // 对oneFuture返回的结果进行加工
                try {
                    String result = oneFuture.get();
                    System.out.println("oneFuture result : " + result);
                    Thread.sleep(1000);
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName());
                System.out.println("--- after oneFuture over doSomething ---");
            }
        });

        // 同步等待twoFuture对应的任务完成，返回结果固定为null
        System.out.println(twoFuture.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // runAsync();
        // runAsyncWithBizExecutor();
        // supplyAsync();
        // supplyAsyncWithBizExecutor();
        // thenRun();
        thenAccept();
    }
}
