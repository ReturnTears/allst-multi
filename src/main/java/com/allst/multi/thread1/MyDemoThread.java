package com.allst.multi.thread1;

/**
 * 多线程基础No.1
 * @author June
 * @date 2018-06-26
 * @version 1.0
 */
public class MyDemoThread extends Thread {

    private int count = 5;

    @Override
    public void run() {
//        synchronized (this) {
            count--;
            System.out.println(currentThread().getName() + " count = " + count);
//        }
    }

    public static void main(String[] args) {
        /**
         * 分析:当多个线程访问MyDemoThread的run方法时，排队的方式进行处理（这里排队是按照CPU分配的先后顺序而定的）
         * 一个线程想要执行synchronized修饰的方法里的代码:
         * 1 尝试获得锁
         * 2 如果拿到锁，执行synchronized代码体内容；拿不到锁，这个线程就会不断的尝试获得这把锁，直到拿到为止，
         *   而且是多个线程同时去竞争这把锁。（也就是会有锁竞争的问题）
         */
        MyDemoThread thread = new MyDemoThread();
        Thread t1 = new Thread(thread, "t1");
        Thread t2 = new Thread(thread, "t2");
        Thread t3 = new Thread(thread, "t3");
        Thread t4 = new Thread(thread, "t4");
        Thread t5 = new Thread(thread, "t5");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        /**
         * 不加锁运行的结果：
         t1 count = 4
         t2 count = 3
         t4 count = 1
         t3 count = 2
         t5 count = 0
         * 或
         t1 count = 3
         t3 count = 3
         t2 count = 2
         t4 count = 1
         t5 count = 0
         *************
         * 加锁运行的结果：出现了锁竞争的问题
         t1 count = 4
         t3 count = 3
         t5 count = 2
         t4 count = 1
         t2 count = 0
         */
    }
}
