package com.allst.multi.threadpool2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Executors获取ExecutorService
 *
 * @author YiYa
 * @since 2020-03-08 下午 03:21
 */
public class MyPoolExecutors {

    public static void main(String[] args) {
        meth6();
    }

    private static void meth1() {
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            es.submit(new MtRunnable(i + 1));
        }
    }

    private static void meth2() {
        ExecutorService es = Executors.newCachedThreadPool(new ThreadFactory() {
            int n = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "自定义Cached线程" + n++);
            }
        });
        for (int i = 0; i < 10; i++) {
            es.submit(new MtRunnable(i + 1));
        }
    }

    private static void meth3() {
        ExecutorService es = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            es.submit(new MtRunnable(i + 1));
        }
    }

    private static void meth4() {
        ExecutorService es = Executors.newFixedThreadPool(3, new ThreadFactory() {
            int n = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "自定义FixedT线程" + n++);
            }
        });
        for (int i = 0; i < 10; i++) {
            es.submit(new MtRunnable(i + 1));
        }
    }

    private static void meth5() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            es.submit(new MtRunnable(i + 1));
        }
    }

    private static void meth6() {
        ExecutorService es = Executors.newSingleThreadExecutor(new ThreadFactory() {
            int n = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "自定义SingleT线程" + n++);
            }
        });
        for (int i = 0; i < 10; i++) {
            es.submit(new MtRunnable(i + 1));
        }
    }

}

class MtRunnable implements Runnable {

    private int id;

    public MtRunnable(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.printf("%s 执行了任务~~~%d\n", name, id);
    }
}