package com.allst.multi.sync;

/**
 * 对象锁: 包括方法锁和同步代码块锁
 * 代码块形式：手动指定锁定对象，也可是是this,也可以是自定义的锁
 *
 * @author June
 * @since 2022-07-28
 */
public class SynchronizedObjectLock2 implements Runnable {

    static SynchronizedObjectLock2 instance = new SynchronizedObjectLock2();

    // 创建两个对象锁
    final Object block1 = new Object();
    final Object block2 = new Object();

    @Override
    public void run() {
        // 这个代码块使用的是第一把锁，当他释放后，后面的代码块由于使用的是第二把锁，因此可以马上执行
        synchronized (block1) {
            System.out.println("block1 ,I`am thread :　" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("block1　,　" + Thread.currentThread().getName() + "　is over...");
        }

        synchronized (block2) {
            System.out.println("block２ ,I`am thread :　" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("block2 , " + Thread.currentThread().getName() + "　is over...");
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
