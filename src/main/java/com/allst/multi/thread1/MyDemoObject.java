package com.allst.multi.thread1;

import com.allst.multi.utils.ThreadPools;

import java.util.concurrent.ExecutorService;

/**
 * 对象锁的同步和异步问题
 * @author June
 * @version 1.0
 * @date 2018-06-26
 */
public class MyDemoObject {

    public synchronized void method1() {
        try {
            System.out.println("method1当前线程名 : " + Thread.currentThread().getName());
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void method2() {
        System.out.println("method2当前线程名 : " + Thread.currentThread().getName());
    }

//    public static void main(String[] args) {
//        final MyDemoObject demo = new MyDemoObject();
//        /**
//         * t1线程先持有object对象的Lock锁，t2线程可以以异步的方式调用对象中的非synchronized修饰的方法
//         * t1线程先持有object对象的Lock锁，t2线程如果在这个时候调用对象中的同步（synchronized）方法则需等待，也就是同步
//         */
//        Thread t1 = new Thread(() ->  {
//           demo.method1();
//        }, "t1");
//
//        Thread t2 = new Thread(() -> {
//            demo.method2();
//        }, "t2");
//
//        Thread t3 = new Thread(() -> {
//            demo.method1();
//        }, "t3");
//
//        t1.start();
//        t2.start();
//        t3.start();
//    }

    public static void main(String[] args) {
        final MyDemoObject demo = new MyDemoObject();
        ExecutorService service = ThreadPools.getInstance();
        service.execute(() -> demo.method1());
        service.execute(() -> demo.method2());
        service.execute(() -> demo.method1());
        service.shutdown();
    }
}
