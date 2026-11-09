package com.allst.jmh.tools;

import com.allst.jmh.entity.ProductPrice;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toList;

/**
 * Cyclic Barrier的使用场景
 * CyclicBarrier 是 Java 中的一个同步辅助类，用于协调多个线程之间的互相等待，直到所有线程都达到一个公共屏障点（Barrier）后再继续执行。
 * 它的名字中 "Cyclic" 表示这个屏障是可以重复使用的，而 "Barrier" 就是屏障的意思。CyclicBarrier 适用于这样的场景：你有一组线程，
 * 它们必须互相等待至某个状态之后才能全部继续执行。
 * <p>
 * Cyclic Barrier与CountDownLatch非常类似，但是它们之间的运行方式以及原理还是存在着比较大的差异的，
 * 并且CyclicBarrier所能支持的功能CountDownLatch是不具备的。比如，CyclicBarrier可以被重复使用，而CountDownLatch当计数器为0的时候就无法再次利用
 *
 * @author Hutu
 * @since 2024-04-21 下午 08:52
 */
public class CyclicBarrierExample1 {

    private static int[] getProductByCategoryId() {
        return IntStream.rangeClosed(1, 10).toArray();
    }

    public static void main(String[] args) {
        // 根据商品品类获取一组商品ID
        final int[] products = getProductByCategoryId();
        // 通过转换将商品编号转换为ProductPrice
        List<ProductPrice> list = Arrays.stream(products).mapToObj(ProductPrice::new).collect(toList());
        // ① 定义CyclicBarrier ，指定list.size()为子任务数量
        final CyclicBarrier barrier = new CyclicBarrier(list.size());
        // ② 用于存放线程任务的list
        final List<Thread> threadList = Lists.newArrayList();
        list.forEach(pp -> {
                Thread thread = new Thread(() -> {
                    System.out.println(pp.getProdID() + "start calculate price.");
                    try {
                        TimeUnit.SECONDS.sleep(current().nextInt(10));
                        if (pp.getProdID() % 2 == 0) {
                            pp.setPrice(pp.getProdID() * 0.9D);
                        } else {
                            pp.setPrice(pp.getProdID() * 0.71D);
                        }
                        System.out.println(pp.getProdID() + "->price calculate completed.");
                    } catch (InterruptedException e) {
                        // ignore exception
                    } finally {
                        try {
                            // ③ 在此等待其他子线程到达barrier point
                            barrier.await();
                        } catch (InterruptedException | BrokenBarrierException ignored) {
                        }
                    }
                });
                threadList.add(thread);
                thread.start();
            }
        );
        // ④ 等待所有子任务线程结束
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("all of prices calculate finished.");
        list.forEach(System.out::println);
    }
}
