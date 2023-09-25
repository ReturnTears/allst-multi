package com.allst.jmh.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Hutu
 * @since 2023-09-25 下午 08:57
 */
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
// 5个线程通知对共享资源进行操作
@Threads(5)
// 多个线程使用同一个实例
@State(Scope.Benchmark)
public class JmhExample04 {
    // 为type提供了四种可配置的参数值
    @Param({"1", "2", "3", "4"})
    private int type;

    private Map<Long, Long> map;

    @Setup
    public void setUp() {
        switch (type) {
            case 1:
                this.map = new ConcurrentHashMap<>();
                break;
            case 2:
                this.map = new ConcurrentSkipListMap<>();
                break;
            case 3:
                this.map = new Hashtable<>();
                break;
            case 4:
                this.map = Collections.synchronizedMap(new HashMap<>());
                break;
            default:
                throw new IllegalArgumentException("Illegal map type.");
        }
    }

    // 只需要一个基准测试方法即可
    @Benchmark
    public void test() {
        this.map.put(System.nanoTime(), System.nanoTime());
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JmhExample04.class.getSimpleName())
                .build();
        new Runner(opts).run();
    }
}
