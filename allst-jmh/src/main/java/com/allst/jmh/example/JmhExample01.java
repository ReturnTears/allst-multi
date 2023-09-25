package com.allst.jmh.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用JMH进行微基准测试
 *
 * @author Hutu
 * @since 2023-09-25 下午 04:05
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JmhExample01 {
    private final static String DATA = "DUMMY DATA";

    private List<String> arrayList;
    private List<String> linkedList;

    @Setup(Level.Iteration)
    public void setUp() {
        this.arrayList = new ArrayList<>();
        this.linkedList = new LinkedList<>();
    }

    @Benchmark
    public List<String> arrayListAdd() {
        this.arrayList.add(DATA);
        return arrayList;
    }

    @Benchmark
    public List<String> linkedListAdd() {
        this.linkedList.add(DATA);
        return this.linkedList;
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts = new
                OptionsBuilder().include(JmhExample01.class.getSimpleName())
                .forks(1)
                .measurementIterations(10) // 度量执行的批次是10，在这10个批次中，对基准方法的执行与调用将会纳入统计
                .warmupIterations(10)   // 在真正的度量之前，首先会对代码进行3个批次的热身，使代码的运行达到JVM已经优化的效果
                .build();
        new Runner(opts).run();
    }
}
