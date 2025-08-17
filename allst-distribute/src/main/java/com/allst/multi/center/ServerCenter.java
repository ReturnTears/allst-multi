package com.allst.multi.center;

import java.io.IOException;

/**
 * @author Hutu
 * @since 2025-07-19 下午 11:13
 */
public interface ServerCenter {
    void stop();
    void start() throws IOException;
    void register(Class<?> serviceInterface, Class<?> impl);
    int getPor();
}
