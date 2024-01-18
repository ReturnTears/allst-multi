package com.allst.async.chapter3;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * 多个CompletableFuture进行组合运算
 *
 * @author Hutu
 * @since 2024-01-13 下午 09:05
 */
public class CompletableFutureMoreCode {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 等待handleOne执行完毕后接着执行handleTwo
        long start = System.currentTimeMillis();
        // 基于thenCompose实现当一个CompletableFuture执行完毕后，执行另外一个CompletableFuture
        //CompletableFuture<String> future = handleOne("123456").thenCompose(id -> handleTwo(id));
        // 基于thenCombine实现当两个并发运行的CompletableFuture任务都完成后，使用两者的结果作为参数再执行一个异步任务
        // CompletableFuture<String> future = handleOne("123456").thenCombine(handleTwo("abcdef"), (one, two) -> one + " : " + two);
        // 基于allOf等待多个并发运行的CompletableFuture任务执行完毕
        // allOf();
        // 基于anyOf等多个并发运行的CompletableFuture任务中有一个执行完毕就返回
        anyOf();
        //System.out.println(future.get());
        long end = System.currentTimeMillis();
        System.out.println("calc result : " + (end - start) / 1000);
    }

    private static void anyOf() throws ExecutionException, InterruptedException {
        List<CompletableFuture<String>> futureList = Lists.newArrayList();
        futureList.add(handleOne("a"));
        futureList.add(handleOne("b"));
        futureList.add(handleTwo("c"));
        futureList.add(handleTwo("d"));
        CompletableFuture<Object> result = CompletableFuture.anyOf(futureList.toArray(new CompletableFuture[0]));
        // 等待第一个future完成
        System.out.println(result.get());
    }

    private static void allOf() throws ExecutionException, InterruptedException {
        List<CompletableFuture<String>> futureList = Lists.newArrayList();
        futureList.add(handleOne("123"));
        futureList.add(handleOne("456"));
        futureList.add(handleOne("789"));
        futureList.add(handleOne("abc"));
        CompletableFuture<Void> future = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
        // 等待所有future都完成
        System.out.println(future.get());
    }

    private static CompletableFuture<String> handleOne(String id) {
        // 异步任务，返回future
        return CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return id;
            }
        });
    }

    private static CompletableFuture<String> handleTwo(String id) {
        // 异步任务，返回future
        return CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    // 模拟计算
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return id + ":handleTwo";
            }
        });
    }
}
