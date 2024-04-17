package com.allst.jmh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Hutu
 * @since 2023-09-25 下午 03:55
 */
@SpringBootApplication
public class JmhApplication {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(JmhApplication.class, args);
    }
}