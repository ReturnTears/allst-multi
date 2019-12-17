package com.allst.multi.basic;

import java.util.concurrent.TimeUnit;

/**
 * Case4
 * @author YiYa
 * @since 2019-12-17 下午 11:21
 */
public class SychCase4 {

    String name;
    double balance;
    public synchronized void set(String name, double balance) {
        this.name = name;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public double getBalance(String name) {
        return this.balance;
    }

    public static void main(String[] args) {
        SychCase4 case4 = new SychCase4();
        new Thread(() -> case4.set("zhan", 100.0)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(case4.getBalance("zhan"));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(case4.getBalance("zhan"));
    }
}
