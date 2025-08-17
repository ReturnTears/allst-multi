package com.allst.multi.provide;

/**
 * @author Hutu
 * @since 2025-07-19 下午 11:10
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello:" + name;
    }
}
