package com.allst.multi.sync;

/**
 * 对象锁: 包括方法锁和同步代码块锁
 * 代码块形式：手动指定锁定对象，也可是是this,也可以是自定义的锁
 *
 * @author June
 * @since 2022-07-28
 */
public class SynchronizedObjectLock implements Runnable {

    static SynchronizedObjectLock instance = new SynchronizedObjectLock();

    @Override
    public void run() {
        // 同步代码块形式--锁为this,两个线程使用的锁是一样的,线程two必须要等到线程one释放了该锁后，才能执行
        synchronized (this) {
            System.out.println("I`am thread : " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " finished...");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.setName("one");
        t1.start();
        t2.setName("two");
        t2.start();
    }
}
