package com.allst.multi.basic;

/**
 * @author Hutu
 * @since 2023-12-19 下午 09:42
 */
public class SychCase7 extends Thread {

    public SychCase7(SychCase7A case7A) {
        super(case7A);
    }

    @Override
    public void run() {
        System.out.println("Thread is executed ");
    }
}
