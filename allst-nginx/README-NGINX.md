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

# Nginx访问FastDFS上的文件
FastDFS官方wiki
    https://github.com/happyfish100/fastdfs/wiki
```text
环境搭建：
本机Windows10、Oracle VM Virtual Box、 Centos7
安装FastDFS步骤：
0、先新建一个数据存储目录
    mkdir /home/dfs #创建数据存储目录
    默认文件安装目录为：/usr/local/src # 切换到安装目录准备下载安装包
1、安装编译环境
    yum install git gcc gcc-c++ make automake vim wget libevent -y
2、安装libfastcommon
    cd /usr/local/src
    git clone https://github.com/happyfish100/libfastcommon.git --depth 1
    cd libfastcommon/
    ./make.sh && ./make.sh install #编译安装
3、安装FastDFS
    cd /usr/local/src
    git clone https://github.com/happyfish100/fastdfs.git --depth 1
    cd fastdfs/
    ./make.sh && ./make.sh install #编译安装
    # 配置文件准备
    编译完成后会在/etc/fdfs/目录下生产如下文件：
     tracker.conf、storage.conf、client.conf # 客户端文件，测试用
    cp /usr/local/src/fastdfs/conf/http.conf /etc/fdfs/ # 供nginx访问使用
    cp /usr/local/src/fastdfs/conf/mime.types /etc/fdfs/ # 供nginx访问使用
4、安装fastdfs-nginx-module
    cd /usr/local/src
    git clone https://github.com/happyfish100/fastdfs-nginx-module.git --depth 1
    cp /usr/local/src/fastdfs-nginx-module/src/mod_fastdfs.conf /etc/fdfs
5、安装nginx
    wget http://nginx.org/download/nginx-1.15.4.tar.gz #下载nginx压缩包
    tar -zxvf nginx-1.15.4.tar.gz #解压
    cd nginx-1.15.4/
    #添加fastdfs-nginx-module模块
    ./configure --add-module=/usr/local/src/fastdfs-nginx-module/src/ 
    make && make install #编译安装
6、单机部署
默认设置服务器IP：
ServerIP: 192.168.0.100
6.1、tracker配置
    #我建议用ftp下载下来这些文件 本地修改
    vim /etc/fdfs/tracker.conf
    #需要修改的内容如下
    port=22122  # tracker服务器端口（默认22122,一般不修改）
    base_path=/home/dfs  # 存储日志和数据的根目录
6.2、storage配置
    vim /etc/fdfs/storage.conf
    #需要修改的内容如下
    port=23000  # storage服务端口（默认23000,一般不修改）
    base_path=/home/dfs  # 数据和日志文件存储根目录
    store_path0=/home/dfs  # 第一个存储目录
    tracker_server=ServerIP:22122  # tracker服务器IP和端口
    http.server_port=8888  # http访问文件的端口(默认8888,看情况修改,和nginx中保持一致)
6.3、client测试
    vim /etc/fdfs/client.conf
    #需要修改的内容如下
    base_path=/home/dfs
    tracker_server=ServerIP:22122    #tracker服务器IP和端口
    #保存后测试,返回ID表示成功 如：group1/M00/00/00/xx.tar.gz
    fdfs_upload_file /etc/fdfs/client.conf my.cnf
6.4、配置nginx访问
    vim /etc/fdfs/mod_fastdfs.conf
    #需要修改的内容如下
    tracker_server=ServerIP:22122  #tracker服务器IP和端口
    url_have_group_name=true
    store_path0=/home/dfs
    #配置nginx.config
    vim /usr/local/nginx/conf/nginx.conf
    #添加如下配置
    server {
        listen       8888;    ## 该端口为storage.conf中的http.server_port相同
        server_name  localhost;
        location ~/group[0-9]/ {
            ngx_fastdfs_module;
        }
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
        root   html;
        }
    }
    #测试下载，用外部浏览器访问刚才已传过的nginx安装包,引用返回的ID
    http://ServerIP:8888/group1/M00/00/00/wKgAQ1pysxmAaqhAAA76tz-dVgg.tar.gz
    #弹出下载单机部署全部跑通
7、启动
7.1、防火墙
    #启动前先关闭防火墙，不关闭防火墙的话无法使用
    systemctl stop firewalld #关闭
    systemctl restart firewalld #重启
    systemctl status firewalld #状态
7.2、tracker
    /usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf start #启动tracker服务
    /usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf restart #重启动tracker服务
    /usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf stop #停止tracker服务
    chkconfig fdfs_trackerd on #自启动tracker服务
7.3、storage
    /usr/bin/fdfs_storaged /etc/fdfs/storage.conf start #启动storage服务
    /usr/bin/fdfs_storaged /etc/fdfs/storage.conf restart #重动storage服务
    /usr/bin/fdfs_storaged /etc/fdfs/tracker.conf stop #停止动storage服务
    chkconfig fdfs_storaged on #自启动storage服务
7.4、nginx
    /usr/local/nginx/sbin/nginx #启动nginx
    /usr/local/nginx/sbin/nginx -s reload #重启nginx
    /usr/local/nginx/sbin/nginx -s stop #停止nginx

查看所有运行的端口：
netstat -ntlp
如提示没有该命令，运行如下命令，等待安装即可：yum install net-tools

8、测试下载
关闭防火墙：systemctl stop firewalld
安装模块fastdfs-nginx-module就是为了让nginx可以http协议访问FastDFS系统中各目录的文件
在6.3中client测试已经上传了一个文件：group1/M00/00/00/wKgAZGEGkaSASP4YAAABGo922tU415.cnf
在浏览器中访问：
http://192.168.0.100:8888/group1/M00/00/00/wKgAZGEGkaSASP4YAAABGo922tU415.cnf
提示下载文件则表示安装，配置，启动，上传，下载成功。

```