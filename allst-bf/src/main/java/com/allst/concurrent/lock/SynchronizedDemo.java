package com.allst.concurrent.lock;

/**
 * 对当前类使用javac 和 javap 命令获得反编译后的字节码信息
 * javac xx.java
 * javap -c -p -s xx.class
 * @author Hutu
 * @since 2025-09-17 下午 10:03
 */
public class SynchronizedDemo {
    public synchronized void method() {
        System.out.println("synchronized embellish method");
    }
}
