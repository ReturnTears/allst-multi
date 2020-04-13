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