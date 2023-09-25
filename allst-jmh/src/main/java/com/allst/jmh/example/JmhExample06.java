package com.allst.jmh.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static java.lang.Math.PI;

/**
 * @author Hutu
 * @since 2023-09-25 下午 09:52
 */
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JmhExample06 {
    @Benchmark
    public void baseline() {
        // 空的方法
    }

    @Benchmark
    public void measureLog1() {
        // 进行数学运算，但是在局部方法内
        Math.log(PI);
    }

    @Benchmark
    public void measureLog2() {
        // result是通过数学运算所得并且在下一行代码中得到了使用
        double result = Math.log(PI);
        // 对result进行数学运算，但是结果既不保存也不返回，更不会进行二次运算
        Math.log(result);
    }

    @Benchmark
    public double measureLog3() {
        // 返回数学运算结果
        return Math.log(PI);
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JmhExample06.class.getSimpleName())
                .build();
        new Runner(opts).run();
    }
}
