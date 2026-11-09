package com.allst.jmh.tools;

import com.allst.jmh.entity.Tourist;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * CyclicBarrier 工作原理
 * CyclicBarrier 内部维护了一个计数器，你可以在构造时指定需要等待的线程数量。每当一个线程到达屏障点时，它会调用 await() 方法，
 * 这个方法会阻塞线程直到所有线程都到达屏障点。如果所有线程都到达了，屏障就会打开，所有被阻塞的线程将被释放，而屏障将被重置以便下次使用。
 * @author Hutu
 * @since 2024-04-21 下午 09:02
 */
public class CyclicBarrierExample2 {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        // 定义CyclicBarrier，注意这里的parties值为11
        final CyclicBarrier barrier = new CyclicBarrier(11);
        // 创建10个线程
        for (int i = 0; i < 10; i++) {
            // 定义游客线程，传入游客编号和barrier
            new Thread(new Tourist(i, barrier)).start();
            TimeUnit.MILLISECONDS.sleep(50); // 给子线程调度窗口
            int numberWaiting = barrier.getNumberWaiting();
            System.out.println("numberWaiting : " + numberWaiting);
        }
        // 主线程也进入阻塞，等待所有游客都上了旅游大巴
        barrier.await();
        System.out.println("Tour Guider:all of Tourist get on the bus.");
        // 主线程进入阻塞，等待所有游客都下了旅游大巴
        barrier.await();
        System.out.println("Tour Guider:all of Tourist get off the bus.");
    }
}
