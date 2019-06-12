package com.allst.multi.thread3;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * PriorityBlockingQueue 一个具有优先级的无限阻塞队列
 * @author June
 * @version 1.0
 * @date 2018-06-30
 */
public class UsePriorityBlockingQueue {

    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();

        Task t1 = new Task();
        t1.setId(18);
        t1.setName("yangyang");

        Task t2 = new Task();
        t2.setId(17);
        t2.setName("June");

        Task t3 = new Task();
        t3.setId(19);
        t3.setName("July");

        queue.add(t1);
        queue.add(t2);
        queue.add(t3);

        System.out.println("容器 : " + queue);
        System.out.println(queue.take().getId());
        System.out.println("容器 : " + queue);
    }
}
