# JMH
```text
JMH是Java Micro Benchmark Harness的简写，是专门用于代码微基准测试的工具集（toolkit）

```
## 设置全局的Warmup和Measurement
```text
final Options opts = new OptionsBuilder()
        .include(JMHExample03.class.getSimpleName())
        .forks(1)
        .measurementIterations(5) // 度量执行的批次为5,也就是
        // 说在这5个批次中，对基准方法的执行与调用将会纳入统计
        .warmupIterations(3)      // 在真正的度量之前，首先会对代码进行3个批次的热身，
        // 使代码的运行达到JVM已经优化的效果
        .build();
new Runner(opts).run();

除了在构造Options时设置Warmup和Measurement，我们还可以通过注解的方式指定预热和度量各自的批次:

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Measurement(iterations = 5) // 度量5个批次

@Warmup(iterations = 3)      // 预热3个批次
public class JMHExample03
```

## 在基准测试方法上设置Warmup和Measurement
```text
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Measurement(iterations = 5)
@Warmup(iterations = 2)
public class JMHExample03 {

    @Benchmark
    public void test() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(10);
    }

    /**
    * 预热5个批次
    * 度量10个批次
    */
    @Measurement(iterations = 10)
    @Warmup(iterations = 5)
    @Benchmark
    public void test2() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1);
    }
```

## 编写正确的微基准测试用例
```text
1、避免DCE（Dead Code Elimination）
   所谓Dead Code Elimination是指JVM为我们擦去了一些上下文无关，甚至经过计算之后确定压根不会用到的代码
2、使用Blackhole
   JMH提供了一个称为Blackhole的类，可以在不作任何返回的情况下避免Dead Code的发生，Blackhole直译为“黑洞”，与Linux系统下的黑洞设备/dev/null非常相似
   Blackhole可以帮助你在无返回值的基准测试方法中避免DC（Dead Code）情况的发生。
3、避免常量折叠（Constant Folding）
   常量折叠是Java编译器早期的一种优化——编译优化。
   在javac对源文件进行编译的过程中，通过词法分析可以发现某些常量是可以被折叠的，也就是可以直接将计算结果存放到声明中，而不需要在执行阶段再次进行运算。
4、避免循环展开（Loop Unwinding）
   
5、Fork用于避免Profile-guided optimizations
   将Fork设置为1的时候，也就是说每一次运行基准测试时都会开辟一个全新的JVM进程对其进行测试

```

## Java 同时提供 CountDownLatch 和 CyclicBarrier
```text
Java 同时提供 CountDownLatch 和 CyclicBarrier,
是因为二者解决的是本质不同的线程协调问题，无法互相替代。根据检索到的 JDK 设计文档与技术资料，核心区别如下：
1、同步模型不同
CountDownLatch：是 “一等多”的单向等待。一个或多个线程调用 await() 阻塞，等待其他线程通过 countDown() 将计数器减至 0。等待者与执行者角色分离，计数器由外部被动递减。
CyclicBarrier：是 “多等多”的双向协同。一组固定数量的线程互相等待，每个线程都必须主动调用 await() 到达屏障点，直到所有参与者都到齐后才集体释放。没有独立的“等待者”角色。
2、可重用性不同
CountDownLatch：一次性使用。计数器归零后不可重置，再次调用 await() 会立即返回。若需重复同步，必须创建新实例。
CyclicBarrier：可循环复用。每次所有线程到达屏障后，内部计数器自动重置为初始值（parties），同一实例可直接用于下一轮同步。还支持 reset() 手动重置以应对异常恢复。
3、屏障动作支持
CountDownLatch：无回调机制。计数器归零仅触发等待线程唤醒，不能自动执行汇总或清理逻辑。
CyclicBarrier：构造时可传入 Runnable barrierAction，该任务会在最后一个线程到达屏障时、所有线程被释放前自动执行，天然适合阶段间的聚合、校验或状态切换操作。
4、底层实现与性能特征
CountDownLatch：基于 AQS 共享模式，纯 CAS + volatile state，无锁路径，开销极低。
CyclicBarrier：基于 ReentrantLock + Condition，涉及加锁/解锁及条件队列管理，功能更丰富但单轮开销略高。其 generation 机制支撑了可循环性和破损检测（BrokenBarrierException）。
5. 典型场景对照
场景	                        CountDownLatch	CyclicBarrier
主线程等待 N 个子任务完成后再汇总	✅	            ❌
多线程分阶段并行计算（每阶段结束需同步再进入下一阶段）	❌	✅
模拟并发测试起跑线（所有线程同时开始）	⚠️ 可用但不自然	✅
需要阶段间自动执行聚合/校验逻辑	❌	✅
仅需一次性等待，追求极致低开销	✅	⚠️ 过重

总结
CountDownLatch 解决的是“等待事件完成”的问题，强调单向、一次性、轻量；CyclicBarrier 解决的是“多方步调一致”的问题，强调双向、可循环、带阶段动作。
二者在 Java 并发工具包中互补共存，而非冗余设计。若需求超出两者能力（如动态增减参与者、多级屏障），应选用 Phaser。

```
