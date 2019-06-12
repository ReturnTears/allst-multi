package com.allst.multi.thread1;

/**
 * synchronized的异常
 * @author June
 * @version 1.0
 * @date 2018-06-26
 */
public class SynchException {
    private int i = 0;
    public synchronized void operation() {
        while (true) {
            try {
                i++;
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " , i = " + i);
                if (i == 20) {
                    throw new RuntimeException();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final SynchException synch = new SynchException();
        Thread t1 = new Thread(() -> {
            synch.operation();
        }, "t1");
        t1.start();
    }
}
