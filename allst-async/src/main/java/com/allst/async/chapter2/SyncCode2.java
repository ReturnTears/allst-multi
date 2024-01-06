package com.allst.async.chapter2;

/**
 * 手动同步编码
 *
 * @author Hutu
 * @since 2024-01-06 下午 09:27
 */
public class SyncCode2 {

    private static void handle1() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Come in handle1 method");
    }

    private static void handle2() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Come in handle2 method");
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(SyncCode2::handle1, "handle1");
        thread.start();
        handle2();
        thread.join();
        System.err.println("It`s Cost times : " + (System.currentTimeMillis() - start));
    }
}
