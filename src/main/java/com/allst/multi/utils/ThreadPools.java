package com.allst.multi.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池
 * @author June
 * @version 1.0
 * @date 2018-07-02
 */
public class ThreadPools {
    /**
     * 初始化线程数量
     */
    private final static int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    /***
     * 自定义一个ThreadFactory
     */
    static final class SimpleThreadFactory implements ThreadFactory {
        private AtomicInteger id = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("thread - " + id.getAndIncrement());
            return thread;
        }
    }
    /**
     * 定义一个线程池
     */
    private static ExecutorService executorService = new ThreadPoolExecutor(THREAD_COUNT,THREAD_COUNT*2,60000,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new SimpleThreadFactory());

    /**
     * 获取线程
     * @return
     */
    public static ExecutorService getInstance() {
        return executorService;
    }

}
