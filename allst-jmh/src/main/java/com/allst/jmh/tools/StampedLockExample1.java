package com.allst.jmh.tools;

import java.util.concurrent.locks.StampedLock;

/**
 * 替代ReentrantLock
 *
 * @author Hutu
 * @since 2024-04-27 下午 08:56
 */
public class StampedLockExample1 {
    // 共享数据
    private static int shareData = 0;

    // 定义StampedLock 锁
    private static final StampedLock lock = new StampedLock();

    private static void inc() {
        // 调用writeLock方法返回一个数据stamp
        long stamp = lock.writeLock();
        try {
            // 修改共享数据
            shareData++;
        } finally {
            // 释放锁
            lock.unlockWrite(stamp);
        }
    }

    private static int get() {
        // 获取锁并记录数据戳
        long stamp = lock.writeLock();
        try {
            // 返回数据
            return shareData;
        } finally {// 释放锁
            lock.unlockWrite(stamp);
        }
    }
}
