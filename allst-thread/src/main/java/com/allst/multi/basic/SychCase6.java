package com.allst.multi.basic;

/**
 * @author Hutu
 * @since 2023-12-19 下午 09:42
 */
public class SychCase6 extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Current Thread isDaemon : " + Thread.currentThread().isDaemon());
    }
}
