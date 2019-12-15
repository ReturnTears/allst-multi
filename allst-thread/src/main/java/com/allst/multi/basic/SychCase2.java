package com.allst.multi.basic;

/**
 * Synchronized关键字,对自身对象加锁
 * @author YiYa
 * @since 2019-12-15 下午 11:37
 */
public class SychCase2 {

    private int count = 10;

    private static int nums = 10;

    public void lockThis() {
        synchronized (this) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);

        }
    }

    /**
     * 上面的方法等同于下面这个方法
     */
    public synchronized void lockThis2() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    /**
     * 这里等同于synchronized(com.allst.multi.basic.SychCase2.class)
     */
    public synchronized static void lockThis3() {
        nums--;
        System.out.println(Thread.currentThread().getName() + " nums = " + nums);

    }

    /**
     * 上述方法等于如下
     */
    public static void lockThis4() {
        synchronized (SychCase2.class) {
            nums--;
        }
    }
}
