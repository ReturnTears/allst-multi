package com.allst.multi.thread1;

import com.allst.multi.utils.ThreadPools;

import java.util.concurrent.ExecutorService;

/**
 * 死锁问题，在设计程序时就应该避免双方相互持有对方的锁的情况
 * 避免死锁的常见方法：
 * 1.	避免一个线程同时获取多个锁。
 * 2.	避免一个线程在锁内同时占用多个资源，尽量保证每个锁只占用一个资源。
 * 3.	尝试使用定时锁，使用lock.tryLock（timeout）来替代使用内部锁机制。
 * 4.	对于数据库锁，加锁和解锁必须在一个数据库连接里，否则会出现解锁失败的情况。
 * jps jstack pid
 * @author June
 * @version 1.0
 * @date 2018-06-29
 */
public class DeadLock implements Runnable {

    private String tag;
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public void run() {
        if ("a".equals(tag)) {
            synchronized (lock1) {
                try {
                    System.out.println("tag_a curr thread : " + Thread.currentThread().getName() + " come in lock1.");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("tag_a curr thread : " + Thread.currentThread().getName() + " come in lock2.");
                }
            }
        }
        if ("b".equals(tag)) {
            synchronized (lock2) {
                try {
                    System.out.println("tag_b curr thread : " + Thread.currentThread().getName() + " come in lock2.");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("tag_b curr thread : " + Thread.currentThread().getName() + " come in lock1.");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLock d1 = new DeadLock();
        d1.setTag("a");
        DeadLock d2 = new DeadLock();
        d2.setTag("b");

        /**
         * tag_a curr thread : t1 come in lock1.
         * tag_b curr thread : t2 come in lock2.
         */
        /**
        Thread t1 = new Thread(d1, "t1");
        Thread t2 = new Thread(d2, "t2");
        t1.start();
        // 休眠500S为了保证t1在t2之前执行
        Thread.sleep(500);
        t2.start();
        */

        // tag_a curr thread : thread - 1 come in lock1.
        // tag_b curr thread : thread - 2 come in lock2.

        ExecutorService service = ThreadPools.getInstance();
        service.execute(d1);
        Thread.sleep(500);
        service.execute(d2);
        service.shutdown();
    }


}
