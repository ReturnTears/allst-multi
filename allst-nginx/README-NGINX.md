# Nginx
```text
https://www.bilibili.com/video/BV1wv411j7Ed?p=3&spm_id_from=pageDriver

Nginx常用功能模块(important)：
静态资源部署
Rewrite地址重写
    正则表达式
反向代理
负载均衡
    轮询、加权轮询、ip_hash、url_hash、fair
web缓存
环境部署
    高可用的环境
用户认证模块

Nginx核心组成：
nginx二进制可执行文件
nginx.conf配置文件
error.log错误的日志记录
access.log访问日志记录

Nginx服务启停命令：
nginx服务的信号控制
1.Nginx中的master和worker进程?
    查看:nginx001.png
2.nginx的工作方式?
    管理员 > master > worker(多个)
    具体的工作方式后续不上
3.如何获取进程的PID?
    1)nginx001.png图中可以查看其pid
    2)more logs/nginx.pid
4.信号有哪些?
    信号          作用
    TERM/INT      立即关闭整个服务
    QUIT          “优雅”地关闭整个服务
    HUP           重读配置文件并使用服务对新配置项生效
    USR1          重新打开日志文件，可以用来进行日志切割
    USR2          平滑升级到最新版的nginx
    WINCH         所有子进程不在接收处理新连接，相当于给work进程发送QUIT指令
    调用命令：kill -single pid
    eg: kill -QUIT 2555
5.如何通过信号控制nginx的启停等相关操作?
nginx服务的命令控制
```

# Nginx访问FasfDFS上的文件
```text

```