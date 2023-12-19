package com.allst.multi.threadgroup;

/**
 * @author Hutu
 * @since 2023-12-19 下午 10:49
 */
public class TgCase1 {
    public static void main(String[] args) {
        ThreadGroup threadGroup1 = new ThreadGroup("my-1");
        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1, "my-2");
        ThreadGroup parent = threadGroup2.getParent();
        while (parent != null) {
            System.out.println(parent);
            parent = parent.getParent();
        }
    }
}
