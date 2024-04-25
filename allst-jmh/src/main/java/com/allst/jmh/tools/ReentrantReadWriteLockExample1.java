package com.allst.jmh.tools;

import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Java读写锁
 *
 * @author Hutu
 * @since 2024-04-25 下午 10:07
 */
public class ReentrantReadWriteLockExample1 {

    // 定义ReadWriteLock 锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    // 创建读锁
    private final Lock readLock = readWriteLock.readLock();
    // 创建写锁
    private final Lock writeLock = readWriteLock.writeLock();
    // 共享数据
    private final LinkedList<String> list = Lists.newLinkedList();

    // 使用写锁进行数据同步
    public void add(String element) {
        writeLock.lock();
        try {
            list.addLast(element);
        } finally {
            writeLock.unlock();
        }
    }

    // 使用写锁进行数据同步
    public String take() {
        writeLock.lock();
        try {
            return list.removeFirst();
        } finally {
            writeLock.unlock();
        }
    }

    // 使用读锁进行数据同步
    public String get(int index) {
        readLock.lock();
        try {
            return list.get(index);
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantReadWriteLockExample1 example = new ReentrantReadWriteLockExample1();
        // 定义10个线程
        for (int i = 0; i < 10; i++) {
            int j = i;
            Thread thread = new Thread(() -> {
                if (j % 3 == 0) {
                    example.add("add" + j);
                }
                /*if (j % 3 == 1) {
                    String take = example.take();
                    System.out.println("result- " + j + " : " + take);
                }*/
                /*if (j % 3 == 2) {
                    String s = example.get(j);
                    System.out.println("s : " + s);
                }*/
            }, "myThread-" + i);
            thread.start();
        }
        System.out.println(example.list);
    }
}
