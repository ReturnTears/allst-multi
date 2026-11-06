package com.allst.async.chapter2;

import lombok.extern.slf4j.Slf4j;

/**
 * 手动编写同步编码
 *
 * @author Hutu
 * @since 2024-01-06 下午 09:27
 */
@Slf4j
public class SyncCode {

    private static void handle1() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Come in handle1 method");
    }

    private static void handle2() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Come in handle2 method");
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        handle1();
        handle2();
        log.info("It`s Cost times : {}", System.currentTimeMillis() - start);
    }
}
