package com.allst.jmh.tools;

import com.google.common.util.concurrent.Monitor;

import static java.lang.Thread.currentThread;

/**
 * @author Hutu
 * @since 2024-04-27 下午 09:14
 */
public class MonitorExample1 {
    // 定义Monitor对象
    private static final Monitor monitor = new Monitor();
    // 共享数据，一个简单的int类型数据
    private static int x = 0;
    // 定义临界值，共享数据的值不能超过MAX_VALUE
    private static final int MAX_VALUE = 10;

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            // 注释②
            monitor.enterWhen(INC_WHEN_LESS_10);
            try {
                x++;
                System.out.println(currentThread() + ": x value is: " + x);
            } finally {
                // 注释③
                monitor.leave();
            }
        }
    }

    // 定义Guard 并且实现isSatisfied方法
    private static final Monitor.Guard INC_WHEN_LESS_10 = new Monitor.Guard(monitor) {
        // 该方法就相当于我们在写对象监视器或者Condition时的临界值判断逻辑
        @Override
        public boolean isSatisfied() {
            return x < MAX_VALUE;
        }
    };
}
