package com.allst.multi.sync;

/**
 * 现象、时机(内置锁this)、深入JVM看字节码(反编译看monitor指令)
 * 编译的时候需要去掉注释， GBK编码编译时会报错.....
 * javac SynchronizedDemo.java
 * javap -verbose SynchronizedDemo.class
 *
 * @author June
 * @since 2022-08-01
 */
public class SynchronizedDemo {

    Object object = new Object();

    public void method1() {
        synchronized (object) {

        }
        method2();
    }

    private static void method2() {

    }
}
