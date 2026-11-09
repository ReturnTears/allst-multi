package com.allst.jmh.tools;

import com.allst.jmh.entity.ProductPrice;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * CountDownLatch的基本使用
 * CountDownLatch 是一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 * 这个类非常适合在某些操作必须等待其他线程完成之后才能继续执行的场景中使用。
 * <p>
 * CountDownLatch 的工作原理是通过一个计数器来实现的，计数器的初始值为线程的数量。
 * 当调用 countDown() 方法时，计数器的值会减一。当计数器的值减到零时，所有在 await() 方法上等待的线程将被唤醒并继续执行。
 * <p>
 * 与 Thread.join 和 CyclicBarrier 相比，CountDownLatch 提供了更灵活的使用方式。它允许在多个线程中分别调用 countDown() 方法，
 * 或者在一个线程中多次调用来减少计数器的值。而 Thread.join 则是不断检查线程是否存活，如果存活则让当前线程永远等待。
 * 相比之下，CountDownLatch 在使用上更为灵活。
 * @author Hutu
 * @since 2024-04-20 上午 11:06
 */
public class CountDownLatchExample1 {
    public static void main(String[] args) throws InterruptedException {
        final int[] products = getProductByCategoryId();
        List<ProductPrice> collect = Arrays.stream(products).mapToObj(ProductPrice::new).collect(Collectors.toList());
        final CountDownLatch latch = new CountDownLatch(products.length);
        collect.forEach(pp ->
                // ② 为每一件商品的计算都开辟对应的线程
                new Thread(() ->
                {
                    System.out.println(pp.getProdID() + "-> start calculate price.");
                    try {
                        // 模拟其他的系统调用，比较耗时，这里用休眠替代
                        TimeUnit.SECONDS.sleep(current().nextInt(10));
                        // 计算商品价格
                        if (pp.getProdID() % 2 == 0) {
                            pp.setPrice(pp.getProdID() * 0.9D);
                        } else {
                            pp.setPrice(pp.getProdID() * 0.71D);
                        }
                        System.out.println(pp.getProdID() + "-> price calculate completed.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // ③ 计数器count down，子任务执行完成
                        latch.countDown();
                    }
                }).start()
        );
        // ④主线程阻塞等待所有子任务结束，如果有一个子任务没有完成则会一直等待
        latch.await();
        System.out.println("all of prices calculate finished.");
        collect.forEach(System.out::println);
    }

    private static int[] getProductByCategoryId() {
        return IntStream.rangeClosed(1, 10).toArray();
    }
}
