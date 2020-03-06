package com.allst.multi.threadpool;

import java.util.List;

/**
 * 线程类
 *
 * @author YiYa
 * @since 2020-03-07 上午 12:02
 */
public class MyWorker extends Thread {

    private String name;
    private List<Runnable> task;

    public MyWorker(String name, List<Runnable> task) {
        this.name = name;
        this.task = task;
    }

    @Override
    public void run() {
        while (task.size() > 0) {
            Runnable r = task.remove(0);
            r.run();
        }
    }
}
