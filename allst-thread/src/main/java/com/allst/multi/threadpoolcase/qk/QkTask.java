package com.allst.multi.threadpoolcase.qk;

/**
 * 取款任务类
 *
 * @author YiYa
 * @since 2020-03-08 下午 09:26
 */
public class QkTask implements Runnable {

    // 金额
    private static double money = 1000;

    private final String account;

    private final double qkMoney;

    public QkTask(String account, double qkMoney) {
        this.account = account;
        this.qkMoney = qkMoney;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        synchronized (QkTask.class) {
            if (money - qkMoney >= 0) {
                money -= qkMoney;
                System.out.println("用户:" + account + "正在使用:" + threadName + "做取款操作, 操作成功,余额: " + money);
            } else {
                System.out.println("用户:" + account + "正在使用:" + threadName + "做取款操作，操作失败，余额不足: 余额: " + money);
            }
        }
    }
}
