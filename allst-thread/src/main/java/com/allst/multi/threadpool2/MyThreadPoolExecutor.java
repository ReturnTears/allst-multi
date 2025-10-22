package com.allst.multi.threadpool2;

import java.util.concurrent.*;

/**
 * 线程池扩展钩子函数
 * 所谓的钩子函数就是线程池在任务执行前或执行后会主动触发一下这个钩子函数，使得线程池能够在任务执行前后有一定的介入能力！
 * 线程池为我们提供的钩子回调分别是afterExecute和beforeExecute。
 *
 * @author Hutu
 * @since 2025-10-22 下午 10:05
 */
public class MyThreadPoolExecutor extends ThreadPoolExecutor {
    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        System.out.println("exec start");
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        System.out.println("exec finished");
        super.afterExecute(r, t);
    }

    public static void main(String[] args) {
        MyThreadPoolExecutor executor = new MyThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        executor.execute(() ->{
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("task finished...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
