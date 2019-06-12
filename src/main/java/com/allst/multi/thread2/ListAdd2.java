package com.allst.multi.thread2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * wait notfiy 方法，wait释放锁，notfiy不释放锁
 * @author June
 * @version 1.0
 * @date 2018-06-29
 */
public class ListAdd2 {

    private volatile static List list = new ArrayList();

    public void add() {
        list.add("xiaohu");
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        final ListAdd2 add2 = new ListAdd2();
        /**
         * 实例化lock
         * 当使用wait 和 notify 的时候 ， 一定要配合着synchronized关键字去使用
         */
        final Object lock = new Object();
        /**
         * CountDownLatch允许一个或多个线程等待其他线程完成操作
         */
        final CountDownLatch latch = new CountDownLatch(1);

        Thread t1 = new Thread(() -> {
            try {
//                synchronized (lock) {
                    for (int i = 0; i < 10; i++) {
                        add2.add();
                        System.out.println("当前线程：" + Thread.currentThread().getName() + "添加了一个元素..");
                        Thread.sleep(500);
                        if (add2.size() == 5) {
                            System.out.println("已经发出通知.");
                            latch.countDown();
//                            lock.notify()
                        }
                    }
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
//            synchronized (lock) {
                if (list.size() != 5) {
                    try {
//                        System.out.println("t2进入...")
//                        lock.wait()
                        /**
                         * CountDownLatch的await方法会阻塞当前线程，直到N变成零
                         */
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("当前线程：" + Thread.currentThread().getName() + "收到通知线程停止..");
                throw new RuntimeException();
//            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
