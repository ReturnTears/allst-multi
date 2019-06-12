package com.allst.multi.thread1;

import com.allst.multi.utils.ThreadPools;

import java.util.concurrent.ExecutorService;

/**
 * 使用synchronized代码块加锁,比较灵活
 * @author June
 * @version 1.0
 * @date 2018-06-29
 */
public class ObjectLock {

    public void method1() {
        // 对象锁
        synchronized (this) {
            try {
                System.out.println("do method1.");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void method2() {
        // 类锁
        synchronized (ObjectLock.class) {
            try {
                System.out.println("do method2.");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Object lock = new Object();
    public void method3() {
        // 任何对象锁
        synchronized (lock) {
            try {
                System.out.println("do method3.");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        final ObjectLock obj = new ObjectLock();
//        Thread t1 = new Thread(() -> {
//            obj.method1();
//        });
//        Thread t2 = new Thread(() -> {
//            obj.method2();
//        });
//        Thread t3 = new Thread(() -> {
//            obj.method3();
//        });
//
//        t1.start();
//        t2.start();
//        t3.start();
//    }

    public static void main(String[] args) {
        final ObjectLock lock = new ObjectLock();
        ExecutorService service = ThreadPools.getInstance();
        service.execute(() -> lock.method1());
        service.execute(() -> lock.method2());
        service.execute(() -> lock.method3());
        service.shutdown();
    }
}
