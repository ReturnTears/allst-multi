package com.allst.multi.thread1;

import com.allst.multi.utils.ThreadPools;

import java.util.concurrent.ExecutorService;

/**
 * 业务整体需要使用完整的synchronized，保持业务的原子性
 * @author June
 * @version 1.0
 * @date 2018-06-26
 */
public class MyDirtyRead {
    private String username = "atmoicity";
    private String password = "root12345";

    public synchronized void setValue(String username, String password) {
        this.username = username;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.password = password;
        System.out.println("setValue最终结果 : username = " + username + ", password = " + password);
    }

    public void getValue() {
        System.out.println("getValue方法得到 : username = " + this.username + " , password = " + this.password);
    }

//    public static void main(String[] args) throws InterruptedException {
//        final MyDirtyRead read = new MyDirtyRead();
//        Thread t1 = new Thread(() -> {
//           read.setValue("yangyang", "1234567");
//        });
//        t1.start();
//        Thread.sleep(1000);
//        read.getValue();
//    }

    public static void main(String[] args) throws InterruptedException {
        final MyDirtyRead read = new MyDirtyRead();
        ExecutorService service = ThreadPools.getInstance();
        service.execute(() -> read.setValue("yangYang", "1311010"));
        Thread.sleep(1000);
        read.getValue();
        service.shutdown();
    }
}
