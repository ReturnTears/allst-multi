package com.allst.netty.protobuf.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author June
 * @since 2021年09月
 */
@SpringBootApplication
public class NettyServerApp {
	public static void main(String[] args) {
		// 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
		ApplicationContext context = SpringApplication.run(NettyServerApp.class, args);
		NettyServer nettyServer = context.getBean(NettyServer.class);
		nettyServer.run();
	}

}
