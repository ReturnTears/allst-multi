package com.allst.multi.basic;

/**
 * @author Hutu
 * @since 2023-12-19 下午 09:44
 */
public class ThreadCase6 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().isDaemon());

        SychCase6 case6 = new SychCase6();
        case6.setDaemon(true);
        /*
        未打印case6中的语句是因为：它已被设置为守护线程，需要等待5s才会输出内容，但是在此过程中唯一的非守护线程main很快执行完毕了，
        所以SychCase6线程对象还在睡眠的过程中，此时的JVM环境里全是守护线程，JVM退出，导致SychCase6线程对象直接被关闭了
         */
        case6.start();
    }
}
