package com.allst.jmh.tools;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * 信号量
 *
 * @author Hutu
 * @since 2024-04-23 下午 09:58
 */
public class SemaphoreExample1 {
    public static void main(String[] args) {
        // 定义许可证数量，最多同时只能有10个用户登录成功并且在线
        final int MAX_PERMIT_LOGIN_ACCOUNT = 10;

        final LoginService loginService = new LoginService(MAX_PERMIT_LOGIN_ACCOUNT);

        // 启动20个线程
        IntStream.range(0, 20).forEach(i ->
            new Thread(() -> {
                // 登录系统，实际上是一次许可证的获取操作
                boolean login = loginService.login();
                // 如果登录失败，则不再进行其他操作
                if (!login) {
                    System.out.println(currentThread() + " is refused due to exceed max online account.");
                    return;
                }
                try {
                    // 简单模拟登录成功后的系统操作
                    simulateWork();
                } finally {
                    // 退出系统，实际上是对许可证资源的释放
                    loginService.logout();
                }
            }, "User-" + i).start()
        );
    }

    // 随机休眠
    private static void simulateWork() {
        try {
            TimeUnit.SECONDS.sleep(current().nextInt(10));
        } catch (InterruptedException e) {
            // ignore
        }
    }

    private static class LoginService {
        private final Semaphore semaphore;

        public LoginService(int maxPermitLoginAccount) {
            // 初始化Semaphore
            this.semaphore = new Semaphore(maxPermitLoginAccount, true);
        }

        public boolean login() {
            // 获取许可证，如果获取失败该方法会返回false，tryAcquire不是一个阻塞方法
            boolean login = semaphore.tryAcquire();
            if (login)
                System.out.println(currentThread() + " login success.");
            return login;
        }

        // 释放许可证
        public void logout() {
            semaphore.release();
            System.out.println(currentThread() + " logout success.");
        }
    }
}
