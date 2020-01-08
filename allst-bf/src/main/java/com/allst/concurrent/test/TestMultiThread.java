package com.allst.concurrent.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 多线程交替执行
 * @author YiYa
 * @since 2020-01-08 下午 11:08
 */
@Slf4j(topic = "c.TestMultiThread")
public class TestMultiThread {

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                log.debug("t1 running....");
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                log.debug("t2 running....");
            }
        }, "t2").start();
    }
}
