package com.allst.multi.thread1;

import com.allst.multi.utils.ThreadPools;

import java.util.concurrent.ExecutorService;

/**
 * synchronized代码块对字符串的锁，注意String常量池的缓存功能
 * @author June
 * @version 1.0
 * @date 2018-06-29
 */
public class StringLock {

    public void method() {
        // new String("字符串常量")
        synchronized ("字符串常量") {
            try {
                while (true) {
                    System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 开始");
                    Thread.sleep(1000);
                    System.out.println("当前线程 : "  + Thread.currentThread().getName() + " 结束");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        final StringLock str = new StringLock();
//        Thread t1 = new Thread(() -> {
//            str.method();
//        }, "t1");
//
//        Thread t2 = new Thread(() -> {
//            str.method();
//        }, "t2");
//
//        t1.start();
//        t2.start();
//    }

    public static void main(String[] args) {
        final StringLock str = new StringLock();
        ExecutorService service = ThreadPools.getInstance();
        service.execute(() -> str.method());
        service.execute(() -> str.method());
        service.shutdown();
    }
}
