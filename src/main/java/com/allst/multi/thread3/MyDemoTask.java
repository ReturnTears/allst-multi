package com.allst.multi.thread3;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author June
 * @version 1.0
 * @date 2018-06-30
 */
public class MyDemoTask implements Delayed {

    private String name;
    /**
     * 身份证
     */
    private String id;
    /**
     * 截止时间
     */
    private long endTime;
    /**
     * 定义时间工具类
     */
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public MyDemoTask(String name,String id,long endTime){
        this.name=name;
        this.id=id;
        this.endTime = endTime;
    }

    public String getName(){
        return this.name;
    }

    public String getId(){
        return this.id;
    }

    /**
     * 用来判断是否到了截止时间
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return endTime - System.currentTimeMillis();
    }

    /**
     * 相互比较排序用
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        MyDemoTask task = (MyDemoTask) o;
        return this.getDelay(this.timeUnit) - task.getDelay(this.timeUnit) > 0 ? 1 : 0;
    }
}
