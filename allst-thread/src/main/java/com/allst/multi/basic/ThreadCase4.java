package com.allst.multi.basic;

import java.util.concurrent.TimeUnit;

/**
 * 在 Java 中，守护线程（Daemon Thread）是一种特殊类型的线程，其作用是为其他线程提供服务和支持。
 * 与普通线程（用户线程）不同，守护线程的生命周期会随着程序的主线程（或最后一个用户线程）的结束而终止。
 * 这意味着当只剩下守护线程在运行时，Java 虚拟机会自动退出。
 * 守护线程通常用于执行后台任务，如垃圾回收、定时任务、监控、日志记录等，它们在后台默默地执行，不会干扰或影响程序的正常执行。
 * 一旦所有用户线程完成了它们的任务并退出，Java 虚拟机就会自动关闭，而不管守护线程是否完成了它们的工作。
 * 以下程序的运行结果：
 * 运行代码后，主线程运行完毕之后，项目会直接结束，并不会存在任何输出和异常，会直接结束运行！这就是守护线程与工作线程最本质的区别：
 * 守护线程： 只要主线程执行完毕，无论守护线程执行与否，都会停止服务。
 * 工作线程： JVM 会等待工作线程全部结束之后才会停止主线程服务。
 *
 * @author Hutu
 * @since 2025-10-14 下午 10:07
 */
public class ThreadCase4 {
    public static void main(String[] args) {
        Thread thread = new Thread(new Task());
        thread.setName("test daemon thread");
        thread.setDaemon(true);
        thread.start();
        System.out.println("main end");
    }

    private static class Task implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread run, name is : " + Thread.currentThread().getName());
        }
    }
}
