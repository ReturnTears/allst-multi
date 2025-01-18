package com.allst.multi.thread1;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Hutu
 * @since 2025-01-18 下午 11:34
 */
public class ForkJoinTest {
    private static double[] d;
    private class ForkJoinTask extends RecursiveTask<Integer> {
        private final int first;
        private final int last;

        public ForkJoinTask(int first, int last) {
            this.first = first;
            this.last = last;
        }

        @Override
        protected Integer compute() {
            int subCount;
            if (last - first < 10) {
                subCount = 0;
                for (int a = first; a <= last; a++) {
                    if (d[a] < 0.5) {
                        subCount++;
                    }
                }
            } else {
                int mid = (first + last) >>> 1;
                ForkJoinTask left = new ForkJoinTask(first, mid);
                left.fork();
                ForkJoinTask right = new ForkJoinTask(mid + 1, last);
                right.fork();
                subCount = left.join();
                subCount += right.join();
            }
            return subCount;
        }
    }

    public void handler() {
        Random random = new Random();
        Stream<Double> stream = Stream.iterate(0.0, i -> random.nextDouble());
        List<Double> collect = stream.limit(10000).collect(Collectors.toList());
        d = new double[collect.size()];
        for (int i = 0; i < collect.size(); i++) {
            d[i] = collect.get(i);
        }
        int n = new ForkJoinPool().invoke(new ForkJoinTask(0, 9999));
        System.out.println("found " + n + " values");
    }

    public static void main(String[] args) {
        new ForkJoinTest().handler();
    }
}
