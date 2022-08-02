package com.allst.multi.sync;

/**
 * 可重入锁，示例加锁解锁过程：
 *
 * 执行monitorenter获取锁
 * 1、（monitor计数器=0，可获取锁）
 * 2、执行method1()方法，monitor计数器+1 -> 1 （获取到锁）
 * 3、执行method2()方法，monitor计数器+1 -> 2
 * 4、执行method3()方法，monitor计数器+1 -> 3
 *
 * 执行monitorexit命令
 * 1、method3()方法执行完，monitor计数器-1 -> 2
 * 2、method2()方法执行完，monitor计数器-1 -> 1
 * 3、method2()方法执行完，monitor计数器-1 -> 0 （释放了锁）
 * 4、（monitor计数器=0，锁被释放了）
 *
 * 这就是Synchronized的重入性，即在同一锁程中，每个对象拥有一个monitor计数器，当线程获取该对象锁后，monitor计数器就会加一，
 * 释放锁后就会将monitor计数器减一，线程不需要再次获取同一把锁。
 *
 * @author June
 * @since 2022-08-02
 */
public class synchronizedReentrant {
    public static void main(String[] args) {
        synchronizedReentrant reentrant = new synchronizedReentrant();
        /**
         * 执行结果：
         * 1: method1()
         * 1: method2()
         * 1: method3()
         */
        reentrant.method1();
    }

    private synchronized void method1() {
        System.out.println(Thread.currentThread().getId() + ": method1()");
        method2();
    }

    private synchronized void method2() {
        System.out.println(Thread.currentThread().getId() + ": method2()");
        method3();
    }

    private synchronized void method3() {
        System.out.println(Thread.currentThread().getId() + ": method3()");
    }
}
