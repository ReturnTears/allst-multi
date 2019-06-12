package com.allst.multi.thread1;

/**
 * synchronized重入
 * @author June
 * @version 1.0
 * @date 2018-06-26
 */
public class SynchDubbo1 {

    public synchronized void method1() {
        System.out.println("method1...");
        method2();
    }

    public synchronized void method2() {
        System.out.println("method2...");
        method3();
    }

    public synchronized void method3() {
        System.out.println("method3...");
    }

    public static void main(String[] args) {
        final SynchDubbo1 dubbo1 = new SynchDubbo1();
        Thread t1 = new Thread(() -> {
            dubbo1.method1();
        });
        t1.start();
    }
}
