package com.allst.multi.thread2;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * queue
 * @author June
 * @version 1.0
 * @date 2018-06-30
 */
public class MyDemoQueue {

    /**
     * 1 需要一个承装元素的集合
     */
    private List<Object> list = new LinkedList<>();

    /**
     * 2 需要一个计数器
     */
    private AtomicInteger count = new AtomicInteger(0);

    /**
     * 3 需要制定上限和下限
     */
    private final int minSize = 0;

    private final int maxSize ;

    /**
     * 4 构造方法
     * @param size
     */
    public MyDemoQueue(int size){
        this.maxSize = size;
    }

    /**
     * 5 初始化一个对象，用于加锁
     */
    private final Object lock = new Object();

    /**
     * put(obj): 把obj加到BlockingQueue里,如果BlockQueue没有空间,
     * 则调用此方法的线程被阻断，直到BlockingQueue里面有空间再继续.
     * @param obj
     */
    public void put(Object obj) {
        synchronized (lock) {
            while (count.get() == this.maxSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 1 加入元素
            list.add(obj);
            // 2 计数器累加
            count.incrementAndGet();
            // 3 通知另外一个线程
            lock.notify();
            System.out.println("新加入的元素为 : " + obj);
        }
    }

    public Object take() {
        Object obj = null;
        synchronized (lock) {
            while (count.get() == this.minSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 1 移除元素操作
            obj = list.remove(0);
            // 2 计数器递减
            count.decrementAndGet();
            // 唤醒另外一个线程
            lock.notify();
        }
        return obj;
    }

    public int getSize() {
        return this.count.get();
    }

    public static void main(String[] args) {
        final MyDemoQueue mq = new MyDemoQueue(5);
        mq.put("a");
        mq.put("b");
        mq.put("c");
        mq.put("d");
        mq.put("e");

        System.out.println("当前容器的长度:" + mq.getSize());

        Thread t1 = new Thread(() -> {
            mq.put("f");
            mq.put("g");
        }, "t1");

        t1.start();

        Thread t2 = new Thread(() -> {
            Object o1 = mq.take();
            System.out.println("移除的元素 : " + o1);
            Object o2 = mq.take();
            System.out.println("移除的元素为 : " + o2);
        }, "t2");

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }
}
