package com.allst.async.chapter2;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 在Java中我们可以使用线程池来实现线程复用，每当我们需要执行异步任务时，可以把任务投递到线程池里进行异步执行。
 * <p>
 * 线程池的拒绝策略设置为CallerRunsPolicy，即当线程池任务饱和，执行拒绝策略时不会丢弃新的任务，而是会使用调用线程来执行
 *
 * @author Hutu
 * @since 2024-01-06 下午 09:52
 */
@Slf4j
public class AsyncThreadPool {
    // 0自定义线程池
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            AVAILABLE_PROCESSORS,                       // 核心线程数
            AVAILABLE_PROCESSORS * 2,                   // 最大线程数
            1,                                          // 线程存活时长
            TimeUnit.MINUTES,                           // 时长单位
            new LinkedBlockingQueue<>(5),       // 任务队列
            new ThreadPoolExecutor.CallerRunsPolicy()   // 拒绝策略
    );

    private static void handle1() {
        log.info("Come in handle1 method start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Come in handle1 method end");
    }

    private static void handle2() {
        log.info("Come in handle2 method start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Come in handle2 method end");
    }

    /**
     * 在当前main方法执行完成后，JVM进程没有退出的原因如下：
     * 1、线程池POOL_EXECUTOR没有传递ThreadFactory，
     *   内部走 Executors.defaultThreadFactory()，它创建的线程 daemon = false。
     * 2、JVM 的退出条件是：所有非守护线程都结束（或调用 System.exit）。
     * 3、main线程跑完started.elapsed这行代码就退出了，但此时：
     *      handle1() 的任务早已执行完，工作线程 pool-1-thread-1 回到 getTask()，
     *      阻塞在 LinkedBlockingQueue.take() 上永久等待下一个任务；
     *      -------------------------------------------------
     *      keepAliveTime = 1 分钟 这个参数对它无效：allowCoreThreadTimeOut 默认 false，
     *      存活超时只回收「超出核心线程数」的那部分线程，核心线程数 = CPU 核数，第一个线程属于核心线程，永远不会被回收；
     *      -------------------------------------------------
     *      于是这个非守护线程一直 WAITING，JVM 就不会退出，进程挂在那里。
     * 4、改进方式有3种
     * 4.1、用完即关掉线程池，使用POOL_EXECUTOR.shutdown(); 如下main方法中代码
     *      Spring Boot 里通常不用手写，把线程池注册成 Bean 并配 @Bean(destroyMethod = "shutdown")，容器关闭时会调用。
     * 4.2、使用自定义线程工厂，并设置为守护线程
     *      NamedThreadFactory.java 的单参构造器默认 daemon = true，直接接上即可。
     *      这样存在一个问题：
     *          main 一结束 JVM 立刻退出，守护线程会被强杀，正在执行或还在队列里的任务直接丢失。
     *         写 demo 图省事可以，处理有副作用的业务任务（写库、发消息）绝对不要用。
     * 4.3、让核心线程也超时回收
     *      POOL_EXECUTOR.allowCoreThreadTimeOut(true); 之后核心线程空闲满 1 分钟也会销毁，进程最终能退出。
     *      但这是「延迟退出」而非「及时退出」，进程要多挂一分钟，一般只用于空闲期释放资源的长驻服务，不解决你这个场景。
     */
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Stopwatch started = Stopwatch.createStarted();
        // 1．开启异步单元执行任务A
        POOL_EXECUTOR.execute(() -> {
            try {
                handle1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // 2．执行任务B
        handle2();
        // 3．同步等待线程A运行结束
        System.out.println(System.currentTimeMillis() - start);
        // 4．挂起当前线程,当挂起main线程时，则JVM将不会退出
        // Thread.currentThread().join();
        started.stop();
        log.info("execute time: {} ms", started.elapsed(TimeUnit.MILLISECONDS));

        POOL_EXECUTOR.shutdown();                                  // 不再接新任务
        if (!POOL_EXECUTOR.awaitTermination(10, TimeUnit.SECONDS)) {
            POOL_EXECUTOR.shutdownNow();                           // 超时则中断
        }
    }
}
