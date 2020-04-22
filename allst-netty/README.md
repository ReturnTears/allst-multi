# Netty的学习之旅
```
https://www.bilibili.com/video/av46753577/?p=8

```
## 学习的关键点:

### 强势技能的学习

### 重视基础

### 节奏感

### 思维方式的改进
    思维方式的改变, 总结遇到的情况，要以不同的角度去思考问题
### 效率工具的使用
    VPN
### 主动输出
    分享

# Netty
    概念:
    Netty is an asynchronous event-driven network application framework 
    for rapid development of maintainable high performance protocol servers & clients.
    Netty是一个异步事件驱动的网络应用程序框架,用于快速开发可维护的高性能协议服务器和客户端。
    
    Netty是一个NIO客户端服务器框架，可以快速轻松地开发协议服务器和客户端等网络应用程序。它极大地简化并简化了TCP和UDP套接字服务器等网络编程。
    
    “快速简便”并不意味着最终的应用程序会受到可维护性或性能问题的影响。Netty经过精心设计，具有丰富的协议，
    如FTP，SMTP，HTTP以及各种二进制和基于文本的传统协议。因此，Netty成功地找到了一种在不妥协的情况下实现易于开发，性能，稳定性和灵活性的方法。
    
    **特征**
    一、设计
        1、适用于各种传输类型的统一API - 阻塞和非阻塞套接字
        2、基于灵活且可扩展的事件模型，可以清晰地分离关注点
        3、高度可定制的线程模型 - 单线程，一个或多个线程池，如SEDA
        4、真正的无连接数据报套接字支持（自3.1起）
    二、便于使用
        1、详细记录的Javadoc，用户指南和示例
        2、没有其他依赖项，JDK 5（Netty 3.x）或6（Netty 4.x）就足够了
        注意：某些组件（如HTTP / 2）可能有更多要求。 有关更多信息，请参阅 “要求”页面。
    三、性能
        1、更高的吞吐量，更低的延迟
        2、减少资源消耗
        3、最小化不必要的内存复制
    四、安全
        1、完整的SSL / TLS和StartTLS支持
    五、社区
        1、早发布，经常发布
        2、自2003年以来，作者一直在编写类似的框架，他仍然发现你的反馈很珍贵！
    
    ---------------------------------------------
    异步同步的区别:
    
    ---------------------------------------------
    事件驱动:
 
## Netty介绍

## Netty架构实现

## Netty模块分析

## Netty Http Tunnel

## Netty对Socket的实现

## Netty压缩与解压缩
    -- 
    对数据传输过程中的压缩
## Netty对于RPC的支持 

## WebSocket实现与原理分析

## WebSocket连接建立方式与生命周期分解

## WebSocket服务端和客户端开发

## RPC框架分析

## Google Protobuf使用方式分析
    
## Apache Thrift使用方式与文件编写方式分析

## Netty大文件传送支持

## 可扩展的事件模型

## Netty统一通信API

## 零拷贝在Netty中的实现与支持

## TCP粘包与拆包分析

## NIO模型在Netty中的实现

## Netty编码开发技术

## Netty重要类与接口源代码剖析

## Channel分析

## 序列化讲解


 
 ## 注意:
    springboot 2.1.5 + netty 4.1.10.Final 会报错java.lang.ClassNotFoundException: jdk.internal.misc.Unsafe
    springboot 2.1.5 + netty 4.1.32.Final
    
```
使用curl发送请求:
curl "http://localhost:8899"    (默认GET请求)
curl -X POST "http://localhost:8899"
curl -X PUT "http://localhost:8899"
curl -X DETELE "http://localhost:8899"
channel的执行顺序流程 : 
channel added           添加         
channel registered      注册
channel active          激活
请求方法名: GET           执行channelRead0
channel inactive        未激活
channel unregistered    未注册

浏览器的执行机制和curl是不一样的

```
    
 ## 韩老师的Netty课程学习
 🍎 Netty的介绍
    -- 1) Netty是由JBOSS提供的一个Java开源框架，现为GitHub上的独立项目。
    -- 2) Netty是一个异步的、基于事件驱动的网络应用框架，用以快速开发高性能、高可靠性的网络IO程序
    -- 3) Netty主要是针对TCP协议下，面向Clients端的高并发应用，或者peer-to-peer场景下的大量数据持续传输的应用。
    -- 4) Neety本质是一个NIO框架，适用于服务器通讯相关的多种应用场景。
    -- 5) 要透彻理解Netty，需要先学习NIO，这样才能阅读Netty的源码
 🍎 Netty的应用场景
    -- 互联网行业：1、分布式系统种，各个节点之间需要远程服务调用，高性能的RPC框架必不可少。2、阿里分布式服务框架Dubbo就用到了Netty
    -- 游戏行业：Netty作为高性能的基础通信组件
    -- 大数据领域：Hadoop的高性能通信和序列化组件（AVRO实现数据我呢见共享）的RPC框架
    >> I/O模型: 
        -- I/O简单理解:用什么样的通道进行数据的发送和接收，很大程度上决定了程序通信的性能
        -- Java共支持3种网络编程模型I/O模式: BIO、NIO、AIO
        -- BIO（同步并阻塞/传统阻塞型），服务器实现模式为一个连接对应一个线程，即客户端有链请求时服务端就需要启动一个线程进行处理
        -- 适用于连接数目较小且固定的架构，这种方式对服务器资源要求比较高，并发局限于应用中，编程比较简单
        -- NIO（同步非阻塞），服务器实现模式为一个线程处理多个请求(连接)，即客户端发送的连接请求都会注册到多路复用器上，多路复用器轮询到连接有I/O请求就进行处理
        -- 适用于连接数目多且连接比较短（轻操作）的架构，比如聊天服务器，弹幕系统，服务器间通讯。编程比较复杂，JDK4开始支持
        -- AIO（异步非阻塞），AIO引入异步通道的概念，采用了Proactor模式，简化了程序编写，有效的请求才启动线程，它的特点时先由操作系统完成后才通知服务端线程启动线程去处理，，一般适用于连接数较多且连接时间较长的应用
        -- 该方式适用于连接数目较多且连接比较长（重操作）的架构， 比如，相册服务器，充分调用OS（操作系统）参与并发操作，编程比较复杂，JDK7开始支持
    >> Java BIO
        就是传统的Java IO编程，开源通过线程池机制改善线程开销问题，实现多客户端连接服务器。
        BIO编程简单流程：
        1、服务器端启动一个ServerSocket
        2、客户端启动Socket对服务器进行通信，默认情况下服务器端需要对每个客户端建立一个线程与之通信
        3、客户端发出请求后，先咨询服务器是否有线程响应，如果没有则会等待，或者被拒绝
        4、如果有响应，客户端线程会等待请求结束后，在继续执行
    >> telnet的使用
        telnet host port - telnet 127.0.0.1 6666
        ctrl + ] 进入交互状态
        send "msg" 发送消息
    >> Java NIO
        全称Java non-blocking IO 是指JDK提供的新API。放在java.nio包及其子包下，对原Java。io包中的很多类进行改写。
        NIO有三大核心部分：Channel(通道)，Buffer(缓冲区)，Selector(选择器)
        NIO是面向缓冲区，或者面向块编程的。数据读取到一个它稍后处理的缓冲区，需要时可在缓冲区种前后移动，
        这就增加了处理过程中的灵活性，使用它可以提供非阻塞式的高伸缩性网络。
        Java NIO的非阻塞模式，是一个线程从某通道发送请求或者读取数据，但是它仅能得到可用的数据，如果目前没有可用数据时，就什么都不会获取。
        而不是保持线程阻塞。所以直至数据变得可以读取之前，该线程还可以做其他的事情。通俗的讲：NIO是可以做到用一个线程来处理多个操作的。
        Http2.0使用了多路复用的技术，做到一个连接并发处理多个请求，而且并发请求的数量比Http1.1大了好几个数量级。
        NIO和BIO的比较：
        -- BIO以流的方式处理数据，而NIO以块的方式处理数据，块I/O的效率比流I/O高很多
        -- BIO是阻塞的，NIO则是非阻塞的
        -- BIO基于字节流和字符流进行操作，而NIO基于Channel和Buffer进行操作， 数据总是从通道读取到缓冲区中，或者从缓冲区中写入到通道中。
           selector用于监听多个通道的事件（连接请求，数据到达），因此使用单个线程就可以监听多个客户端通道。
        NIO三大核心关系：
        selector channel buffer
        -- 每个channel都会对应一个buffer
        -- selector对应一个线程，一个线程对应多个channel（连接），或者说一个selector对应多个channel，又或者说多个channel注册到了一个selector
        -- 程序切换到那个channel是由事件决定的，event是一个重要的概念
        -- selector会根据不同的事件，在各个通道上切换
        -- buffer就是一个内存块，底层是有一个数组
        -- 数据的读取写入是通过buffer， 这个和BIO是有区别的，BIO要么是输入流，要么是输出流，不能双向。但是NIO的buffer是可以读写的，需用到flip()切换读写模式。
        -- channel是双向的，可以返回底层操作系统的情况，比如Linux：底层的操作系统通道就是双向的。
        缓冲区（Buffer）
        缓冲区（Buffer）：缓冲区本质上是一个可以读写数据的内存块，可以理解成是一个容器对象（含数组），该对象提供了一组方法，可以轻松的使用内存块，
                        缓冲区对象内置了一些机制，能够跟踪和记录缓冲区的状态变化情况。channel提供从文件，网络读取数据的渠道，但是读取或写入都必须经由Buffer
                        
                
        
        
        
 🍎 Netty核心模块
 🍎 Google Protobuf
 🍎 Netty编解码器和handler的调用机制（important）
 🍎 TCP粘包与拆包及解决方案
 🍎 Netty核心源码剖析
   >> Netty启动过程源码剖析
   >> Netty接受请求过程源码剖析
   >> Netty的FastThreadLocal源码分析
   >> Netty在Dubbo中应用源码分析
   >> Netty心跳（heartbeat）服务源码剖析
   >> Netty核心组件EventLoop源码剖析
 🍎 
 
 ## Netty从入门到精通
 ```text
@ https://www.bilibili.com/video/BV19Z4y1x7da?p=2
```
 ### 第一章：初识Netty背景、现状、与趋势
 ```text
Netty是2004年开发的
本质：网络应用程序框架
实现：异步、事件驱动
特性：高性能、可维护、快速开发
用途：开发服务器和客户端

为什么舍近求远？而不直接使用JDK NIO呢？
Netty做的更多：
·支持常用应用层协议
·解决传输问题：粘包，半包现象
·支持流量整形
·完善的断连、Idle等异常处理
Netty做的更好之一：规避JDK NIO bug
Netty做的更好之二：API更友好更强大
·JDK的NIO一些API不够友好，功能薄弱，例如ByteBuffer -> Netty`s ByteBuf
·除了NIO外，也提供了其他一些增强：ThreadLocal -> Netty`s FastThreadLocal
Netty做的更好之三：隔离变化、屏蔽细节
·隔离JDK NIO的实现变化： NIO -> NIO2(aio) ->...
·屏蔽JDK NIO的实现细节

其他的网络编程应用框架：
·apache Mina
·Sun Grizzly
·Apple swift NIO、ACE
·Cindy
·tomcat jetty

Netty的前尘往事
Netty归属于JBoss

Netty的现状与趋势
https://github.com/netty/netty
依赖项中声明io.netty:netty-all
使用Netty的典典型项目：
数据库：Cassandra
大数据处理：Spark、Hadoop
Message Queue: RocketMQ
检索：Elasticsearch
框架：gRPC、Apache Dubbo、Spring5
分布式协调器: Zookeeper
工具类：async-http-client
其他参考：https://netty.io/wiki/adopters.html
Netty的趋势：
更多流行协议的支持
紧跟JDK新功能步伐
更多易用、人性化的功能
IP地址黑白名单、流量整形
应用越来越多
```
### 第二章：Netty源码从"点"（领域知识）的角度剖析
```text
Netty怎么切换I/O模式
什么是经典的三种I/O模式？
BIO（阻塞I/O） JDK1.4-
NIO（非阻塞I/O）JDK1.4+ java.nio包
AIO（异步I／O）JDK1.7+

阻塞与非阻塞：阻塞：在没有数据传递过来时，读会阻塞直到有数据；缓冲区满时，写操作也会阻塞。
             非阻塞遇到这些情况，都是直接返回。
同步与异步：数据就绪后需要自己去读是同步，数据就绪直接读好再回调给程序是异步。

Netty对三种I/O模式的支持
为什么Netty仅支持NIO了？
为什么Netty有多种NIO实现？
NIO一定优于BIO么？
BIO代码简单
特定场景：连接数少，并发度低，BIO性能不输NIO
源码解读Netty怎么切换I/O模式？

```

### 第三章：Netty源码从"线"（请求处理）的角度剖析
```text

```

### 第四章：Netty实战
```text

```

### 第五章：Netty实战进阶
```text


```
### 第六章：赏析高级应用是如何使用Netty的
```text

```
