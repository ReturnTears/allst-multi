package com.allst.concurrent.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试类2 - 创建线程方式2, 推荐使用, 他把创建线程和业务逻辑分开了
 * @author YiYa
 * @since 2020-01-08 下午 10:43
 */
@Slf4j(topic = "c.Test2")
public class Test2 {

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                log.debug("running...");
            }
        };
        // lambda简化
        Runnable r2 = () -> log.debug("running......");

        Thread t = new Thread(r2, "t2");
        t.start();

        //
        log.debug("running...");
    }

}
