package com.allst.concurrent.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试类1 - 创建线程方式1
 * @author YiYa
 * @since 2020-01-08 下午 10:39
 */
@Slf4j(topic = "c.Test1")
public class Test1 {

    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                log.debug("running");
            }
        };
        t.setName("t1");
        t.start();

        //
        log.debug("running");
    }

}
