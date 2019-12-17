package com.allst.multi.basic;

/**
 * 执行两个同步方法
 * @author YiYa
 * @since 2019-12-17 下午 11:11
 */
public class SychCase3 {

    public synchronized void mth1() {
        System.out.println(Thread.currentThread().getName() + " mth1 start...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " mth1 end");
    }

    public void mth2() {
        System.out.println(Thread.currentThread().getName() + " mth2 start...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " mth2 end");
    }

    public static void main(String[] args) {
        SychCase3 case3 = new SychCase3();
        // lambda表达式
        /*new Thread(() -> case3.mth1(), "t1").start();
        new Thread(() -> case3.mth2(), "t2").start();*/
        // lambda表达式简写
        new Thread(case3::mth1, "t1").start();
        new Thread(case3::mth2, "t2").start();
    }
}
