package com.allst.multi.thread5;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author June
 * @version 1.0
 * @date 2018-07-01
 */
public class UseThreadPoolExecutor2 implements Runnable {

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void run() {
        try {
            int temp = count.incrementAndGet();
            System.out.println("任务" + temp);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> queue =
                //new LinkedBlockingQueue<Runnable>();
                new ArrayBlockingQueue<>(10);
        ExecutorService executor  = new ThreadPoolExecutor(
                5, 		//core
                10, 	//max
                120L, 	//2fenzhong
                TimeUnit.SECONDS,
                queue);

        for(int i = 0 ; i < 20; i++){
            executor.execute(new UseThreadPoolExecutor2());
        }
        Thread.sleep(1000);
        System.out.println("queue size:" + queue.size());		//10
    }

}
