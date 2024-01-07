package com.allst.async.chapter3;

import java.util.concurrent.*;

/**
 * FutureTask代表了一个可被取消的异步计算任务，该类实现了Future接口，比如提供了启动和取消任务、查询任务是否完成、获取计算结果的接口。
 * 也可以把FutureTask提交到线程池来执行。
 *
 * @author Hutu
 * @since 2024-01-07 下午 09:09
 */
public class AsyncFutureCode3 {

    // 0自定义线程池
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1,
            TimeUnit.MINUTES, new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

    private static String doHandle1() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--- Come in method doHandle1 ---");

        return "doHandle1 method result";
    }

    private static String doHandle2() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--- Come in method doHandle2 ---");
        return "doHandle2 method result";

    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        // 1.创建future任务
        // 2.使用线程池异步单元执行任务A
        Future<String> futureTask = POOL_EXECUTOR.submit(() -> {
            String result = null;
            try {
                result = doHandle1();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        });
        // 3.执行任务B
        String method2Result = doHandle2();
        // 4.同步等待线程A运行结束
        String method1Result = futureTask.get();
        //5.打印两个任务执行结果
        System.out.println(method1Result + " " + method2Result);
        System.out.println(System.currentTimeMillis() - start);
    }
}
