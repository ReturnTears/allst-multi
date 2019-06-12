package com.allst.multi.thread1;

/**
 * synchronized的重入
 * @author June
 * @version 1.0
 * @date 2018-06-26
 */
public class SynchDubbo2 {

    static class Main {
        public int i = 10;
        public synchronized void operationSup() {
            try {
                i--;
                System.out.println("Main print i = " + i);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Sub extends Main {
        public synchronized void operationSub() {
            try {
                while (i > 0) {
                    i--;
                    System.out.println("Sub  print i = " + i);
                    Thread.sleep(100);
                    this.operationSup();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            Sub sub = new Sub();
            sub.operationSub();
        });
        t1.start();
    }
}
