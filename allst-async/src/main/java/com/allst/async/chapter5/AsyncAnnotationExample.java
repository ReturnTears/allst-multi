package com.allst.async.chapter5;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author Hutu
 * @since 2024-01-24 下午 08:51
 */
@EnableAsync // 开启异步执行
@Component // 注入该Bean到Spring容器
public class AsyncAnnotationExample {
    @Async
    public CompletableFuture<String> handle1() {
        // 创建Future
        CompletableFuture<String> result = new CompletableFuture<>();
        try {
            // 模拟任务执行
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " handle1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result.complete("done");
        // 返回结果
        return result;
    }

    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor e = new ThreadPoolTaskExecutor();
        e.setCorePoolSize(4);
        e.setMaxPoolSize(8);
        e.setQueueCapacity(100);
        e.setThreadNamePrefix("async-");
        e.initialize();
        e.setAllowCoreThreadTimeOut(true);
        return e;
    }
}
