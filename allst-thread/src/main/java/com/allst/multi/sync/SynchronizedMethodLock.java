package com.allst.multi.sync;

/**
 * 方法锁: synchronized修饰普通方法，锁对象默认为this
 *
 * @author June
 * @since 2022-07-28
 */
public class SynchronizedMethodLock implements Runnable {

    static SynchronizedMethodLock instance = new SynchronizedMethodLock();

    @Override
    public void run() {
        method();
    }

    public synchronized void method() {
        System.out.println("我是线程 : " + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 结束");
    }


    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.setName("one");
        t1.start();
        t2.setName("two");
        t2.start();
    }
}
