package com.allst.multi.thread1;

import com.allst.multi.utils.ThreadPools;

import java.util.concurrent.ExecutorService;

/**
 * 锁的对象改变问题
 * @author June
 * @version 1.0
 * @date 2018-06-26
 */
public class LockObjectChange {
    private String lock = "lock";

    private void method() {
        synchronized (lock) {
            try {
                System.out.println(lock + " curr thread : " + Thread.currentThread().getName() + " start...");
                lock = "change lock";
                Thread.sleep(2000);
                System.out.println(lock + " curr thread : " + Thread.currentThread().getName() + " end.....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        final LockObjectChange change = new LockObjectChange();
//        Thread t1 = new Thread(() -> {
//            change.method();
//        }, "t1");
//
//        Thread t2 = new Thread(() -> {
//            change.method();
//        }, "t2");
//
//        t1.start();
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        t2.start();
//    }

    public static void main(String[] args) throws InterruptedException {
        final LockObjectChange change = new LockObjectChange();
        ExecutorService service = ThreadPools.getInstance();
        service.execute(() -> change.method());
        Thread.sleep(1000);
        service.execute(() -> change.method());
        service.shutdown();
    }
}
