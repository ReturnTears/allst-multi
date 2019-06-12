package com.allst.multi.thread5;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author June
 * @version 1.0
 * @date 2018-07-01
 */
public class UseThreadPoolExecutor1 {

    private ThreadPoolExecutor pool;

    public static void main(String[] args) {
        /**
         * coreSize, MaxSize, 60, 指定一种队列 （有界队列）,
         * 如果创建新线程将使当前运行的线程超出maximumPoolSize，任务将被拒绝，
         * 并调用RejectedExecutionHandler.rejectedExecution()方法
         */
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1,
                                                     2,
                                                       60,
                                                      TimeUnit.SECONDS,
                           new ArrayBlockingQueue<Runnable>(3),
                                                     new MyRejected());
        MyTask task1 = new MyTask(1, "task1");
        MyTask task2 = new MyTask(2, "task2");
        MyTask task3 = new MyTask(3, "task3");
        MyTask task4 = new MyTask(4, "task4");
        MyTask task5 = new MyTask(5, "task5");
        MyTask task6 = new MyTask(6, "task6");

        pool.execute(task1);
        pool.execute(task2);
        pool.execute(task3);
        pool.execute(task4);
        pool.execute(task5);
        pool.execute(task6);

        pool.shutdown();
    }
}
