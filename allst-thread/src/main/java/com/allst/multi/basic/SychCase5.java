package com.allst.multi.basic;

import java.util.concurrent.TimeUnit;

/**
 * synchronized是可以重入锁的, 即获得锁以后可以再获取锁,
 * @author YiYa
 * @since 2019-12-17 下午 11:37
 */
public class SychCase5 {

    synchronized void mth1() {
        System.out.println("mth1 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mth2();
    }

    synchronized void mth2() {
        System.out.println("mth2 start");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("mth2");
    }

}
