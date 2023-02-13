package com.allst.multi.thread6;

/**
 * @author Hutu
 * @since 2023-02-13 下午 09:51
 */
public class MyThread {
    public static void main(String[] args) {
        System.out.println("main method executed by thread : " + Thread.currentThread().getName());
        Helper helper = new Helper("Hello MyThread ");
        helper.run();
        /*
            上述代码运行结果如下：
            main method executed by thread : main
            doSomething method executed by thread : main
            something message : Hello MyThread
         */

        // 创建一个线程
        Thread thread = new Thread(helper);

        // 设置线程名
        thread.setName("defined-thread");

        // 启动线程
        thread.start();

        /*
            上述代码运行结果如下：
            main method executed by thread : main
            doSomething method executed by thread : main
            something message : Hello MyThread
            doSomething method executed by thread : defined-thread
            something message : Hello MyThread
         */
    }

    static class Helper implements Runnable {

        private final String message;

        Helper(String message) {
            this.message = message;
        }

        private void doSomething(String message) {
            System.out.println("doSomething method executed by thread : " + Thread.currentThread().getName());
            System.out.println("something message : " + message);
        }

        @Override
        public void run() {
            doSomething(message);
        }
    }
}
