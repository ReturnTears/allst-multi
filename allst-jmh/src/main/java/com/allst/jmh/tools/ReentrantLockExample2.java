package com.allst.jmh.tools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.currentThread;

/**
 * 程序会出现死锁的问题，当死锁出现的时候，JVM进程是正常运行的，但是工作线程会因为进入阻塞而不能继续工作。
 *
 * @author Hutu
 * @since 2024-04-25 下午 09:34
 */
public class ReentrantLockExample2 {
    // 分别定义两个lock
    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                method1();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                method2();
            }
        }).start();
    }

    private static void method1() {
        lock1.lock();
        System.out.println(currentThread() + " get lock1.");
        try {
            lock2.lock();
            System.out.println(currentThread() + " get lock2.");
            try {
            } finally {
                lock2.unlock();
                System.out.println(currentThread() + " release lock2.");
            }
        } finally {
            lock1.unlock();
            System.out.println(currentThread() + " release lock1.");
        }
    }

    private static void method2() {
        lock2.lock();
        System.out.println(currentThread() + " get lock2.");
        try {
            lock1.lock();
            System.out.println(currentThread() + " get lock1.");
            try {
            } finally {
                lock1.unlock();
                System.out.println(currentThread() + " release lock1.");
            }
        } finally {
            lock2.unlock();
            System.out.println(currentThread() + " release lock2.");
        }
    }
}
