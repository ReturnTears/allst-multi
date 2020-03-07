package com.allst.multi.threadpool;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 自定义线程池类
 * 成员变量:
 * 🌙  任务队列 集合 需要控制线程安全问题
 * 🌙  当前线程数量
 * 🌙  核心线程数量
 * 🌙  最大小线程数
 * 🌙  任务队列长度
 * 成员方法:
 * ✈  提交任务：将任务添加到集合中，需要判断是否超出了任务总长度
 * 🚗  执行任务：判断当前线程的数量，决定创建核心线程还是非核心线程
 *
 * @author YiYa
 * @since 2020-03-07 下午 11:36
 */
public class MyThreadPool {
    // 任务队列
    private List<Runnable> tasks = Collections.synchronizedList(new LinkedList<>());
    // 当前线程数
    private int nums;
    // 核心线程数
    private int corePoolSize;
    // 最大线程数
    private int maxSIze;
    // 任务队列长度
    private int workSize;

    public MyThreadPool(int corePoolSize, int maxSIze, int workSize) {
        this.corePoolSize = corePoolSize;
        this.maxSIze = maxSIze;
        this.workSize = workSize;
    }

    // 提交任务
    public void submit(Runnable r) {
        if (tasks.size() >= workSize) {
            System.out.println("任务: " + r + " 被丢弃了~~~");
        } else {
            tasks.add(r);
            execTask(r);
        }
    }

    // 执行任务
    private void execTask(Runnable r) {
        if (nums < corePoolSize) {
            new MyWorker("核心线程:" + nums, tasks).start();
            nums++;
        } else if (nums < maxSIze) {
            new MyWorker("非核心线程: " + nums, tasks).start();
        } else {
            System.out.println("任务: " + r + " 被缓存了~~~");
        }
    }

}
