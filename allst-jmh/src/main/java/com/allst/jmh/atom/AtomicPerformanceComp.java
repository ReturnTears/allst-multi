package com.allst.jmh.atom;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 性能比较：Synchronized Vs Lock Vs AtomicInteger
 *
 * @author Hutu
 * @since 2024-04-17 下午 11:04
 */

// 度量批次为10次
@Measurement(iterations = 10)
// 预热批次为10次
@Warmup(iterations = 10)
// 采用平均响应时间作为度量方式
@BenchmarkMode(Mode.AverageTime)
// 时间单位为微秒
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class AtomicPerformanceComp {
    @State(Scope.Group)
    public static class IntMonitor {
        private int x;
        private final Lock lock = new ReentrantLock();

        // 使用显式锁Lock进行共享资源同步
        public void lockInc() {
            lock.lock();
            try {
                x++;
            } finally {
                lock.unlock();
            }
        }

        // 使用synchronized关键字进行共享资源同步
        public void synInc() {
            synchronized (this) {
                x++;
            }
        }
    }

    // 直接采用AtomicInteger
    @State(Scope.Group)
    public static class AtomicIntegerMonitor {
        private AtomicInteger x = new AtomicInteger();

        public void inc() {
            x.incrementAndGet();
        }
    }

    // 基准测试方法
    @GroupThreads(10)
    @Group("sync")
    @Benchmark
    public void syncInc(IntMonitor monitor) {
        monitor.synInc();
    }

    // 基准测试方法
    @GroupThreads(10)
    @Group("lock")
    @Benchmark
    public void lockInc(IntMonitor monitor) {
        monitor.lockInc();
    }

    // 基准测试方法
    @GroupThreads(10)
    @Group("atomic")
    @Benchmark
    public void atomicIntegerInc(AtomicIntegerMonitor monitor) {
        monitor.inc();
    }

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(AtomicPerformanceComp.class.getSimpleName())
                .forks(1)
                .timeout(TimeValue.seconds(10))
                .addProfiler(StackProfiler.class)
                .build();
        new Runner(opts).run();
    }
}
