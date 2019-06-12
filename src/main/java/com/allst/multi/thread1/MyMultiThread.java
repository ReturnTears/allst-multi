package com.allst.multi.thread1;

import com.allst.multi.utils.ThreadPools;

import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

/**
 * 关键字synchronized取得的锁都是对象锁,而不是把一段代码(方法)当作锁
 * 所以代码中哪个线程先执行synchronized关键字的方法，哪个线程就持有该方法所属对象的锁(Lock)
 *
 * 在静态方法上加synchronized关键字，表示锁定.class类，类一级别的锁（独占.class类）
 * @author June
 * @date 2018-06-26
 * @version 1.0
 */
public class MyMultiThread {

    private static final Logger logger = Logger.getLogger("");

    private int num = 0;

    public synchronized void printNum(String tag) {
        try {
            if ("a".equals(tag)) {
                num = 100;
                System.out.println("tag a, set num over!");
                Thread.sleep(1000);
            } else {
                num = 200;
                System.out.println("tag b, set num over!");
            }
            System.out.println("tag " + tag + ", num = " + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        final MyMultiThread m1 = new MyMultiThread();
//        final MyMultiThread m2 = new MyMultiThread();
//
//        Thread t1 = new Thread(() -> {
//            m1.printNum("a");
//        });
//
//        Thread t2 = new Thread(() -> {
//            m2.printNum("b");
//        });
//
//        t1.start();
//        t2.start();
//    }

    public static void main(String[] args) {
        final MyMultiThread thread = new MyMultiThread();
        ExecutorService service = ThreadPools.getInstance();
        service.execute(() -> thread.printNum("a"));
        service.execute(() -> thread.printNum("b"));
        service.shutdown();
    }
}
