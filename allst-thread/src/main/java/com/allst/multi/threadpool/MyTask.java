package com.allst.multi.threadpool;

/**
 * 自定义任务类
 *
 * @author YiYa
 * @since 2020-03-06 下午 11:55
 */
public class MyTask implements Runnable {

    // 任务编号
    private int taskId;

    public MyTask(int taskId) {
        this.taskId = taskId;
    }

    /**
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.printf("线程: %s 即将执行任务~%d", name, taskId);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("线程: %s 完成任务~%d", name, taskId);
    }
}
