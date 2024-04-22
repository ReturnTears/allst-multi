package com.allst.jmh.tools;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * @author Hutu
 * @since 2024-04-22 下午 08:45
 */
public class ExchangerExample1 {
    public static void main(String[] args) {
        // 定义Exchanger类，该类是一个泛型类，String类型标明一对线程交换的数据只能是String类型
       final Exchanger<String> exchanger = new Exchanger<>();
       // 定义线程1
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(currentThread() + " : thread t1 start.");
                try {
                    randomSleep();
                    // 执行exchange方法，将对应的数据传递给T2线程，同时从T2线程获取交换的数据
                    String data = "thread t1 exchange data";
                    // exchangeData为从线程2返回的数据
                    String exchangeData = exchanger.exchange(data);
                    System.out.println(currentThread() + " : thread2 exchange data is : " + exchangeData);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(currentThread() + " : thread t1 end");
            }
        }, "T1");
        thread1.start();

        // 定义线程2
        Thread thread2 = new Thread(() -> {
            System.out.println(currentThread() + " : thread t2 start.");
            try {
                randomSleep();
                // 执行exchange方法，将对应的数据传递给T1线程，同时从T1线程获取交换的数据
                String data = "thread t2 exchange data";
                // exchangeData为从线程1返回的数据
                String exchangeData = exchanger.exchange(data);
                System.out.println(currentThread() + " : thread1 exchange data is : " + exchangeData);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(currentThread() + " : thread t2 end.");
        }, "T2");
        thread2.start();
   }

   private static void randomSleep() {
        try {
            TimeUnit.SECONDS.sleep(current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
   }
}
