package com.allst.multi.thread5;

import com.allst.multi.thread4.Main;

import java.util.concurrent.*;

/**
 * @author June
 * @version 1.0
 * @date 2018-07-01
 */
public class UseFuture implements Callable<String> {

    private String param;

    public UseFuture(String param) {
        this.param = param;
    }

    @Override
    public String call() throws Exception {
        // 模拟执行耗时
        Thread.sleep(5000);
        String result = this.param + " 处理完成";
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String queryStr = "query";
        //构造FutureTask，并且传入需要真正进行业务逻辑处理的类,该类一定是实现了Callable接口的类
        FutureTask<String> future = new FutureTask<>(new UseFuture(queryStr));

        FutureTask<String> future2 = new FutureTask<>(new UseFuture(queryStr));
        //创建一个固定线程的线程池且线程数为1,
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //这里提交任务future,则开启线程执行RealData的call()方法执行
        //submit和execute的区别： 第一点是submit可以传入实现Callable接口的实例对象， 第二点是submit方法有返回值

        Future f1 = executor.submit(future);		//单独启动一个线程去执行的
        Future f2 = executor.submit(future2);
        System.out.println("请求完毕");

        try {
            //这里可以做额外的数据操作，也就是主程序执行其他业务逻辑
            System.out.println("处理实际的业务逻辑...");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //调用获取数据方法,如果call()方法没有执行完成,则依然会进行等待
        System.out.println("数据：" + future.get());
        System.out.println("数据：" + future2.get());

        executor.shutdown();
    }
}
