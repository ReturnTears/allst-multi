package com.allst.concurrent.thread;

import java.time.LocalDateTime;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier是Java中多线程并发编程中的一个工具类，它可以让一组线程到达一个同步点后再同时继续执行。
 * CyclicBarrier可以用于多线程计算数据，最后合并计算结果的场景。
 * 使用CyclicBarrier步骤：
 * <p>
 * 创建CyclicBarrier对象，指定等待线程数和响应动作
 * 创建一组线程，并启动
 * 等待所有线程执行完毕后合并结果
 *
 * @author Hutu
 * @since 2023-12-25 下午 08:44
 */
public class CyclicBarrierDemo {

    static CyclicBarrier barrier = new CyclicBarrier(3);

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            try {
                LocalDateTime now = LocalDateTime.now();
                System.out.println("thread1 is waiting : " + now);
                barrier.await();
                System.out.println("thread1 goes");
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
                LocalDateTime now = LocalDateTime.now();
                System.out.println("thread2 is waiting : " + now);
                barrier.await();
                System.out.println("thread2 goes");
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread3 = new Thread(() -> {
            try {
                Thread.sleep(4000);
                LocalDateTime now = LocalDateTime.now();
                System.out.println("thread3 is waiting : " + now);
                barrier.await();
                System.out.println("thread3 goes");
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();

//        barrier.reset();
//        System.out.println(barrier.getParties());
    }
}
