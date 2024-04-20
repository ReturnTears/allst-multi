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
 *
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
