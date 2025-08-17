# Java 并发编程
```

一个sychronized代码块是原子操作的


```

## allst-distribute
```text
rpc框架分为三部分
1、服务器提供者，运行在服务器端，提供服务接口定义与服务实现类
2、服务中心，运行在服务器端，负责将本地服务发布成远程服务，管理远程服务，提供给服务消费者使用
3、服务消费者，运行在客户端，通过远程代理对象调用远程服务

```

## 🍎 allst-bf 并发相关
[allst-bf](allst-bf/README.md)
[allst-concurrent](allst-bf/README-CONCURRENT.md)

## 🍎 allst-jmh JMH相关
[allst-jmh](allst-jmh/README.md)

## 🍎 allst-netty netty相关
[allst-netty](allst-netty/README.md)



## 🍎 allst-nginx nginx相关
[allst-nginx](allst-nginx/README-NGINX.md)



## 🍎 allst-thread 线程相关
[allst-thread](allst-thread/README.md)


## Java多线程与并发
```text
多线程与并发相关内容查看 allst-bf 模块 和 allst-thread 模块

带着问题发现世界：
1、多线程的出现是要解决什么问题的?
2、线程不安全是指什么? 
3、并发出现线程不安全的本质什么? 
4、Java是怎么解决并发问题的? 
5、线程安全是不是非真即假? 
6、线程安全有哪些实现思路?
7、如何理解并发和并行的区别?


Executors.newSingleThreadExecutor()线程池有以下特点：
1、单线程化的线程池中的人物是按照提交的次序顺序执行的
2、池中的唯一线程的存活时间是无限的
3、当池中的唯一线程正繁忙时，新提交的任务实例会进入内部的阻塞队列中，并且其阻塞队列是无界的
单线程化的线程池所适合的场景是：任务按照提交次序，一个任务一个任务的逐个执行的场景。

Executors.newFixedThreadPool(3)线程池的特点：
1、如果线程数没有达到“固定数量”，每次提交一个任务线程池内就创建一个新线程，直到线程达到线程池固定的数量。
2、线程池的大小一旦达到“固定数量”就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。
3、在接收异步任务的执行目标实例时，如果池中的所有线程均在繁忙状态，新任务会进入阻塞队列中（无界的阻塞队列）。
“固定数量的线程池”的适用场景：需要任务长期执行的场景。“固定数量的线程池”的线程数能够比较稳定地保证一个数，
能够避免频繁回收线程和创建线程，故适用于处理CPU密集型的任务，在CPU被工作线程长时间占用的情况下，能确保尽可能少地分配线程。
“固定数量的线程池”的弊端：内部使用无界队列来存放排队任务，当大量任务超过线程池最大容量需要处理时，队列无限增大，使服务器资源迅速耗尽。

Executors.newCachedThreadPool()线程池的特点：
1、在接收新的异步任务target执行目标实例时，如果池内所有线程繁忙，此线程池就会添加新线程来处理任务。
2、此线程池不会对线程池大小进行限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。
3、如果部分线程空闲，也就是存量线程的数量超过了处理任务数量，就会回收空闲（60秒不执行任务）线程。
“可缓存线程池”的适用场景：需要快速处理突发性强、耗时较短的任务场景，如Netty的NIO处理场景、REST API接口的瞬时削峰场景。
“可缓存线程池”的线程数量不固定，只要有空闲线程就会被回收；接收到的新异步任务执行目标，查看是否有线程处于空闲状态，
如果没有就直接创建新的线程。
“可缓存线程池”的弊端：线程池没有最大线程数量限制，如果大量的异步任务执行目标实例同时提交，可能会因创建线程过多而导致资源耗尽。

Executors.newScheduledThreadPool()创建“可调度线程池”
当被调任务的执行时间大于指定的间隔时间时，ScheduleExecutorService并不会创建一个新的线程去并发执行这个任务，而是等待前一次调度执行完毕。
“可调度线程池”的适用场景：周期性地执行任务的场景。Spring Boot中的任务调度器，底层借助了JUC的ScheduleExecutorService“可调度线程池”实现，
并且可以通过@Configuration配置类型的Bean。

线程池的标准创建方式：
// 使用标准构造器构造一个普通的线程池
public ThreadPoolExecutor(
int corePoolSize,                       // 核心线程数，即使线程空闲(Idle)，也不会回收
int maximumPoolSize,                    // 线程数的上限
long keepAliveTime, TimeUnit unit,      // 线程最大空闲(Idle)时长 
BlockingQueue<Runnable> workQueue,      // 任务的排队队列
ThreadFactory threadFactory,            // 新线程的产生方式
RejectedExecutionHandler handler)       // 拒绝策略

1 核心和最大线程数量
参数corePoolSize用于设置核心(Core)线程池数量，参数maximumPoolSize用于设置最大线程数量。
线程池执行器将会根据corePoolSize和maximumPoolSize自动维护线程池中的工作线程，大致规则为：
(1)当在线程池接收到新任务，并且当前工作线程数少于corePoolSize时，即使其他工作线程处于空闲状态，
也会创建一个新线程来处理该请求，直到线程数达到corePoolSize。
(2)如果当前工作线程数多于corePoolSize数量，但小于maximumPoolSize数量，那么仅当任务排队队列已满时才会创建新线程。
通过设置corePoolSize和maximumPoolSize相同，可以创建一个固定大小的线程池。
(3)当maximumPoolSize被设置为无界值（如Integer.MAX_VALUE）时，线程池可以接收任意数量的并发任务。
(4)corePoolSize和maximumPoolSize不仅能在线程池构造时设置，也可以调用setCorePoolSize()和setMaximumPoolSize()两个方法进行动态更改。

2 BlockingQueue
BlockingQueue（阻塞队列）的实例用于暂存接收到的异步任务，如果线程池的核心线程都在忙，那么所接收到的目标任务缓存在阻塞队列中。

3 keepAliveTime
线程构造器的keepAliveTime（空闲线程存活时间）参数用于设置池内线程最大Idle（空闲）时长（或者说保活时长），
如果超过这个时间，默认情况下Idle、非Core线程会被回收。
如果池在使用过程中提交任务的频率变高，也可以调用方法setKeepAliveTime(long,TimeUnit)进行线程存活时间的动态调整，
可以将时长延长。如果需要防止Idle线程被终止，可以将Idle时间设置为无限大,具体设置如下：
setKeepAliveTime(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
默认情况下，Idle超时策略仅适用于存在超过corePoolSize线程的情况。但若调用了allowCoreThreadTimeOut(boolean)方法，
并且传入了参数true，则keepAliveTime参数所设置的Idle超时策略也将被应用于核心线程。

向线程池提交任务的两种方式：
方式一：调用execute()方法
方式二：调用submit()方法
submit()和execute()两类方法的区别在哪里呢？大致有以下三点：
(1)二者所接收的参数不一样
Execute()方法只能接收Runnable类型的参数，而submit()方法可以接收Callable、Runnable两种类型的参数。
Callable类型的任务是可以返回执行结果的，而Runnable类型的任务不可以返回执行结果。
(2)submit()提交任务后会有返回值，而execute()没有
(3)submit()方便Exception处理


```












-- old
    >> Java中druid和thread-pool的使用
    ###`MultiThread`的使用
    ###回顾`logback`的使用
    ###`Druid`
    ###`thread-pool`
    
## Offen Use
```text
🐟  idea提取代码快捷键: ctrl + alt +m
```