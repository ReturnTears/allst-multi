package com.allst.multi.threadpool2;

import java.util.concurrent.*;

/**
 * 异步计算结果
 * @author YiYa
 * @since 2020-03-08 下午 04:29
 */
public class MyFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建线程池
        ExecutorService es = Executors.newCachedThreadPool();
        // 创建callable类型的任务对象
        Future<Integer> future = es.submit(new MyCall(1, 7));

        // 正在执行的任务被取消
        // boolean b = future.cancel(true);
        // System.out.println(b);

        boolean done = future.isDone();
        System.out.println("任务是否完成~ " + done);
        boolean rs = future.isCancelled();
        System.out.println("任务是否取消~ " + rs);
        // 一致等待任务的执行， 直到任务完成
        Integer v = future.get();
        System.out.println("任务执行结果: " + v);
        boolean isd = future.isDone();
        System.out.println("任务是否完成~ " + isd);
    }

}

class MyCall implements Callable<Integer> {

    private int a;
    private int b;

    public MyCall(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Integer call() throws Exception {
        String name = Thread.currentThread().getName();
        System.out.println("计算开始~~~");
        Thread.sleep(2000);
        System.out.println("计算完成~~~");
        return a + b;
    }
}