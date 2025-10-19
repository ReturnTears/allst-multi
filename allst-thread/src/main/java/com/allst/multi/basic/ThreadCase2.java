package com.allst.multi.basic;

/**
 * @author Hutu
 * @since 2025-10-13 下午 10:41
 */
public class ThreadCase2 {
    public static void main(String[] args) {
        Thread thread = new Thread(new Task());
        thread.setName("test thread");
        thread.start();
        System.out.println("main thread");
    }
    private static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("thread run, thread name is:" + Thread.currentThread().getName());
        }
    }
}
