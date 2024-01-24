package com.allst.async.chapter5;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * @author Hutu
 * @since 2024-01-24 下午 09:08
 */
public class TestaskExecutor {
    public static void main(String[] args) {
        // 1.创建容器上下文
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"beans-annotation.xml"});

        // 2. 获取AsyncExecutorExample实例并调用打印方法
        System.out.println(Thread.currentThread().getName() + " begin ");
        AsyncAnnotationExample asyncCommentExample = applicationContext.getBean(AsyncAnnotationExample.class);

        // 3.获取异步future并设置回调
        CompletableFuture<String> resultFuture = asyncCommentExample.handle1();
        resultFuture.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String t, Throwable u) {
                if (null == u) {
                    System.out.println(Thread.currentThread().getName() + " " + t);
                } else {
                    System.out.println("error:" + u.getLocalizedMessage());
                }
            }
        });

        System.out.println(Thread.currentThread().getName() + " end ");
    }
}
