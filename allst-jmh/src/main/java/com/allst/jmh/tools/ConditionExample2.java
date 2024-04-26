package com.allst.jmh.tools;

import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * @author Hutu
 * @since 2024-04-26 下午 10:15
 */
public class ConditionExample2 {
    // 定义显式锁
    private static final ReentrantLock lock = new ReentrantLock();
    // 创建与显式锁Lock关联的Condition对象
    private static final Condition condition = lock.newCondition();
    // 定义long型数据的链表
    private static final LinkedList<Long> list = Lists.newLinkedList();
    // 链表的最大容量为100
    private static final int CAPACITY = 100;
    // 定义数据的初始值为0
    private static long i = 0;

    // 生产者方法
    private static void produce() {
        // 获取锁
        lock.lock();
        try {
            // 链表数据大于等于100为一个临界值，当list中的数据量达到100时，生产者线程将被阻塞加入与condition关联的wait队列中
            while (list.size() >= CAPACITY) {
                condition.await();
            }
            // 当链表中的数据量不足100时，生产新的数据
            i++;
            // 将数据放到链表尾部
            list.addLast(i);
            System.out.println(currentThread().getName() + "->" + i);
            // ① 通知其他线程
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    // 消费者方法
    private static void consume() {
        // 获取锁
        lock.lock();
        try {
            // 链表为空是另外一个临界值，当list中的数据为空时，消费者线程将被阻塞加入与condition关联的wait队列中
            while (list.isEmpty()) {
                condition.await();
            }
            // 消费数据
            Long value = list.removeFirst();
            System.out.println(currentThread().getName() + "->" + value);
            // ② 通知其他线程
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(current().nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
            throws InterruptedException {
        // 启动10个生产者线程
        IntStream.range(0, 10).forEach(i ->
                new Thread(() -> {
                    for (; ; ) {
                        produce();
                        sleep();
                    }
                }, "Producer-" + i).start()
        );
        // 启动5个消费者线程
        IntStream.range(0, 5).forEach(i ->
                new Thread(() -> {
                    for (; ; ) {
                        consume();
                        sleep();
                    }
                }, "Consumer-" + i).start()
        );
    }
}
