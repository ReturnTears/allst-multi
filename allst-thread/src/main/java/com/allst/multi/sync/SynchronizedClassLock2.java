package com.allst.multi.sync;

/**
 * 类锁: 指synchronize修饰静态的方法或指定锁对象为Class对象
 *
 * @author June
 * @since 2022-07-28
 */
public class SynchronizedClassLock2 implements Runnable {

    static SynchronizedClassLock2 instance1 = new SynchronizedClassLock2();
    static SynchronizedClassLock2 instance2 = new SynchronizedClassLock2();

    @Override
    public void run() {
        // 所有线程需要的锁都是同一把
        synchronized (SynchronizedClassLock2.class) {
            System.out.println("我是线程 : " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 结束");
        }
    }

    public static void main(String[] args) {
        // 串行执行
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.setName("one");
        t1.start();
        t2.setName("two");
        t2.start();
    }
}
