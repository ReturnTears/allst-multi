package com.allst.multi.sync;

/**
 * 类锁: 指synchronize修饰静态的方法或指定锁对象为Class对象
 *
 * @author June
 * @since 2022-07-28
 */
public class SynchronizedClassLock implements Runnable {

    static SynchronizedClassLock instance1 = new SynchronizedClassLock();
    static SynchronizedClassLock instance2 = new SynchronizedClassLock();

    @Override
    public void run() {
        // method1();
        method2();
    }

    /**
     *
     */
    public synchronized void method1() {
        System.out.println("我是线程 : " + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 结束");
    }

    /**
     * synchronized用在静态方法上，默认的锁就是当前所在的Class类，所以无论是哪个线程访问它，需要的锁都只有一把
     */
    public static synchronized void method2() {
        System.out.println("我是线程 : " + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 结束");
    }

    public static void main(String[] args) {
        // run方法执行method1时： t1和t2对应的this是两个不同的实例，所以代码不会串行, 执行method2时， 串行执行
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.setName("one");
        t1.start();
        t2.setName("two");
        t2.start();
    }
}
