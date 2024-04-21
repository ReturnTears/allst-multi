package com.allst.jmh.tools;

import com.allst.jmh.entity.Tourist;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
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
        }
        // 主线程也进入阻塞，等待所有游客都上了旅游大巴
        barrier.await();
        System.out.println("Tour Guider:all of Tourist get on the bus.");
        // 主线程进入阻塞，等待所有游客都下了旅游大巴
        barrier.await();
        System.out.println("Tour Guider:all of Tourist get off the bus.");
    }
}
