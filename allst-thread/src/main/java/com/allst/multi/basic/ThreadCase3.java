package com.allst.multi.basic;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Hutu
 * @since 2025-10-14 下午 09:53
 */
public class ThreadCase3 {
    public static void main(String[] args) {
        System.out.println("main thread start");
        //构建一个具有返回结果的任务对象  包装实际的任务对象
        FutureTask<String> stringFutureTask = new FutureTask<>(new TaskReturn());
        Thread thread = new Thread(stringFutureTask);
        thread.setName("test thread");
        thread.start();
        System.out.println("main thread");
        try {
            System.out.println(stringFutureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("main thread end");
    }

    private static class TaskReturn implements Callable<String> {
        @Override
        public String call() throws Exception {
            return String.format("I had performed by %s", Thread.currentThread().getName());
        }
    }
}
