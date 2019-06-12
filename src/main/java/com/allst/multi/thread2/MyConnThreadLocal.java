package com.allst.multi.thread2;

/**
 * ThreadLocal 线程变量，是一个以ThreadLocal对象为键、任意对象为值的存储结构。
 * 这个结构被附带在线程上，也就是说一个线程可以根据一个ThreadLocal对象查询到绑定在这个线程上的一个值。
 * @author June
 * @version 1.0
 * @date 2018-06-30
 */
public class MyConnThreadLocal {

    public static ThreadLocal<String> th = new ThreadLocal<>();

    public void setTh(String value) {
        th.set(value);
    }

    public void getTh() {
        System.out.println(Thread.currentThread().getName() + " - " + th.get());
    }
    public MyConnThreadLocal() {
    }

    /**
     * 可以通过set(T)方法来设置一个值，在当前线程下再通过get()方法获取到原先设置的值
     * @param args
     */
    public static void main(String[] args) {
        final MyConnThreadLocal local = new MyConnThreadLocal();
        Thread t1 = new Thread(() -> {
            local.setTh("xiaohu");
            local.getTh();
        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                local.getTh();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
