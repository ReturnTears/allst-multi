package com.allst.concurrent.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子更新基本类型
 * AtomicBoolean
 * AtomicInteger
 * AtomicLong
 *
 * @author June
 * @since 2021年07月
 */
@Slf4j
public class AtomicBaseType {

    private static final AtomicInteger ai = new AtomicInteger(1);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        System.out.println(ai.get());
        log.info("getAndIncrement: {}", ai.getAndIncrement());
        log.info("get: {}", ai.get());
    }
}
