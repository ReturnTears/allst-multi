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
        handleTwo();
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
            System.out.println("------"+Thread.currentThread().getName() + " set future result ---------");
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
            System.out.println("------"+Thread.currentThread().getName() + " set future result ---------");
        }, "thread-two").start();
        // 等待计算结果
        // System.out.println(future.get());

        // 当出现异常时返回默认值
        System.out.println(future.exceptionally(t -> "").get());
    }
}
