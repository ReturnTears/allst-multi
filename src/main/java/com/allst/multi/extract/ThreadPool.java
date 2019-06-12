package com.allst.multi.extract;

import java.util.concurrent.*;

/**
 * 线程池
 * @author June
 * @version 1.0
 * @date 2018-06-27
 */
public class ThreadPool {
    /**
     * 线程池数量
     */
    private static int DEFAULT_POOL_NUMBER = 8;
    /**
     * 线程池对象
     */
    private static ThreadPool instance = new ThreadPool();
    /**
     * ExecutorService接口
     */
    private ExecutorService insertDataPool;
    private ExecutorService readDataPool;

    /**
     * 无参构造初始化读写线程池对象
     */
    public ThreadPool() {
        insertDataPool = Executors.newFixedThreadPool(DEFAULT_POOL_NUMBER);
        readDataPool = Executors.newFixedThreadPool(DEFAULT_POOL_NUMBER);
    }

    /**
     * 获取ThreadPool对象
     * @return
     */
    public static ThreadPool getInstance() {
        return instance;
    }

    /**
     * 写入线程调度器
     * @param command
     */
    public void dispatchInsertDataThread(Runnable command){
        insertDataPool.execute(command);
    }

    /**
     * 读取线程调度器
     * @param command
     */
    public void dispatchReadDataThread(Runnable command){
        readDataPool.execute(command);
    }
}
