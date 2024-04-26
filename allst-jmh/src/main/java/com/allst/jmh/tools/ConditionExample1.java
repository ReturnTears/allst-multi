package com.allst.jmh.tools;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * @author Hutu
 * @since 2024-04-26 下午 09:44
 */
public class ConditionExample1 {

    // 定义共享数据
    private static int shareData = 0;
    // 定义布尔便量标识当前的共享数据是否已经被使用
    private static boolean isUsed = false;
    // 创建显示锁
    private static final Lock lock = new ReentrantLock();
    // 使用显示锁创建Condition对象对象并且与之关联
    private static final Condition condition = lock.newCondition();
    // 对数据的写操作
    private static void write() {
        // 获取锁， 如果当前锁被其他线程持有，则当前线程会进入阻塞
        lock.lock();
        try {
            // 如果共享数据未被使用，则当前线程将进入wait队列，并且释放lock
            while (!isUsed) {
                condition.await();
            }
            TimeUnit.SECONDS.sleep(current().nextInt(5));
            // 模拟数据写入,并将isUsed状态标识为false
            shareData = (int) (Math.random() * 100);
            isUsed = false;
            System.out.println("write data is : " + shareData);
            // 通知并唤醒在wait队列中的其他线程：数据读取线程
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    // 对数据的读操作
    private static void read() {
        // 获取锁,如果当前锁被其他线程持有，则当前线程会进入阻塞
        lock.lock();
        try {
            while (isUsed) {
                condition.await();
            }
            // 使用数据，并且将dataUsed状态标识置为true
            TimeUnit.SECONDS.sleep(current().nextInt(5));
            isUsed = true;
            System.out.println("read data is : " + shareData);
            // 通知并唤醒wait队列中的其他线程：数据写入线程
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            lock.unlock();
        }
    }
    public static void main(String[] args) {
        // 创建并启动两个匿名线程
        new Thread(() -> {
            while (true) {
                write();
            }
        }, "Producer").start();
        new Thread(() -> {
            while (true) {
                read();
            }
        }, "Consumer").start();
    }
}
