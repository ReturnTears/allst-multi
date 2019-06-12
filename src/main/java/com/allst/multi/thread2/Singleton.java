package com.allst.multi.thread2;

/**
 * @author June
 * @version 1.0
 * @date 2018-06-30
 */
public class Singleton {

    private static class InnerSingleton {
        private static Singleton singleton = new Singleton();
    }

    public static Singleton getInstance() {
        return InnerSingleton.singleton;
    }
}
