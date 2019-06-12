package com.allst.multi.mutil;

import java.util.Random;

/**
 * @author June
 * @version 1.0
 * @date 2018-07-01
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("我的机器可用Processor数量:" + Runtime.getRuntime().availableProcessors());
        Master master = new Master(new MyWorker(), Runtime.getRuntime().availableProcessors());
        Random r = new Random();
        for(int i = 1; i<= 100; i++){
            Task t = new Task();
            t.setId(i);
            t.setName("任务"+i);
            t.setPrice(r.nextInt(1000));
            master.submit(t);
        }
        master.execute();
        long start = System.currentTimeMillis();
        while(true){
            if(master.isComplete()){
                long end = System.currentTimeMillis() - start;
                int ret = master.getResult();
                System.out.println("最终结果：" + ret + "， 执行耗时：" + end);
                break;
            }
        }
    }
}
