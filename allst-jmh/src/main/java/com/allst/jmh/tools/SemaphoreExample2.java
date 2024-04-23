package com.allst.jmh.tools;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * 借助Semaphore提供的方法实现一个显式锁，该锁的主要作用是try锁，若获取不到锁就会立即返回
 *
 * 运行结果：
 * Thread[main,5,main] get the lock and do working...
 * Thread[Thread-0,5,main]can't get the lock, will do other thing.
 * Thread[main,5,main] release lock
 *
 * @author Hutu
 * @since 2024-04-23 下午 10:08
 */
public class SemaphoreExample2 {
    public static void main(String[] args) {
        final TryLock tryLock = new TryLock();
        // 启动一个线程，尝试获取tryLock，如果获取不成功则将进行其他的操作，该线程不用进入阻塞状态
        new Thread(() -> {
            boolean gotLock = tryLock.tryLock();
            if (!gotLock) {
                System.out.println(currentThread() + "can't get the lock, will do other thing.");
                return;
            }
            try {
                simulateWork();
            } finally {
                tryLock.unlock();
            }
        }).start();
        // main线程也会参与tryLock的争抢，同样，如果抢不到tryLock，则main线程不会进入阻塞状态
        boolean gotLock = tryLock.tryLock();
        if (!gotLock) {
            System.out.println(currentThread() + " can't get the lock, will do other thing.");
        } else {
            try {
                simulateWork();
            } finally {
                tryLock.unlock();
            }
        }
    }

    // 定义tryLock类
    private static class TryLock {
        // 定义permit为1的semaphore
        private final Semaphore semaphore = new Semaphore(1);

        public boolean tryLock() {
            return semaphore.tryAcquire();
        }

        public void unlock() {
            semaphore.release();
            System.out.println(currentThread() + " release lock");
        }
    }

    private static void simulateWork() {
        try {
            System.out.println(currentThread() + " get the lock and do working...");
            TimeUnit.SECONDS.sleep(current().nextInt(10));
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
