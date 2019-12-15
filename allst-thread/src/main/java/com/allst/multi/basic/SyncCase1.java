package com.allst.multi.basic;

/**
 * Synchronized关键字,对某个对象加锁
 * @author YiYa
 * @since 2019-12-15
 */
public class SyncCase1 {

    private int count = 0;
    private Object o = new Object();

    /**
     * 任何线程要执行下面的代码都必须要拿到o的锁
     */
    public void lockObj() {
        synchronized (o) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }

}
