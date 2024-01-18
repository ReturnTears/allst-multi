package com.allst.async.chapter3;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 异常处理
 *
 * @author Hutu
 * @since 2024-01-18 下午 10:14
 */
public class FutureExceptionCode {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        two2();
    }

    private static void handleOne() throws ExecutionException, InterruptedException {
        // 创建一个CompletableFuture对象
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                //Thread.sleep(2000);
                if (true) {
                    throw new RuntimeException("calc exception");
                }
                future.complete("handleOne result");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("------" + Thread.currentThread().getName() + " set future result ---------");
        }, "thread-one").start();
        // 由于报错后这里会一直阻塞
        System.out.println(future.get());
    }

    private static void handleTwo() throws ExecutionException, InterruptedException {
        // 创建一个CompletableFuture对象
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                //Thread.sleep(2000);
                if (true) {
                    throw new RuntimeException("calc exception");
                }
                future.complete("handleTwo result");
            } catch (Exception e) {
                // 设置异常结果
                future.completeExceptionally(e);
            }
            System.out.println("------" + Thread.currentThread().getName() + " set future result ---------");
        }, "thread-two").start();
        // 等待计算结果
        // System.out.println(future.get());

        // 当出现异常时返回默认值
        System.out.println(future.exceptionally(t -> "").get());
    }

    private static void one() throws InterruptedException, ExecutionException {
        // 1.创建一个CompletableFuture对象
        CompletableFuture<String> future = new CompletableFuture<>();
        // 2.开启线程计算任务结果，并设置
        new Thread(() -> {
            // 2.1休眠3s，模拟任务计算
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 2.2设置计算结果到future
            System.out.println("----" + Thread.currentThread().getName() + " set future result----");
            future.completeExceptionally(new RuntimeException("error exception"));
        }, "thread-1").start();

        // 3.等待计算结果
        System.out.println("---main thread wait future result---");
        System.out.println(future.get());
        // System.out.println(future.get(1000,TimeUnit.MILLISECONDS));
        System.out.println("---main thread got future result---");
    }

    public static void two() throws InterruptedException, ExecutionException {
        // 1.创建一个CompletableFuture对象
        CompletableFuture<String> future = new CompletableFuture<>();
        // 2.开启线程计算任务结果，并设置
        new Thread(() -> {
            // 2.1休眠3s，模拟任务计算
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 2.2设置计算结果到future
            System.out.println("----" + Thread.currentThread().getName() + " set future result----");
            future.completeExceptionally(new RuntimeException("error exception"));
        }, "thread-1").start();
        // 3.等待计算结果
        System.out.println("---main thread wait future result---");
        System.out.println(future.exceptionally(t -> "default").get());// 默认值
        // System.out.println(future.get(1000,TimeUnit.MILLISECONDS));
        System.out.println("---main thread got future result---");
    }

    public static void two2() throws InterruptedException, ExecutionException {
        // 1.创建一个CompletableFuture对象
        CompletableFuture<String> future = new CompletableFuture<>();
        // 2.开启线程计算任务结果，并设置
        new Thread(() -> {
            // 2.1休眠3s，模拟任务计算
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 2.2设置计算结果到future
            System.out.println("----" + Thread.currentThread().getName() + " set future result----");
            future.completeExceptionally(new RuntimeException("error exception"));
        }, "thread-1").start();

        // 3.等待计算结果
        System.out.println("---main thread wait future result---");
        System.out.println(future.exceptionally(t -> "default").get());// 默认值
        // System.out.println(future.get(1000,TimeUnit.MILLISECONDS));
        System.out.println("---main thread got future result---");
    }
}
