package com.allst.multi.thread3;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 即写时复制
 * @author June
 * @version 1.0
 * @date 2018-06-30
 */
public class UseCopyOnWrite {

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> copyList = new CopyOnWriteArrayList<>();
        CopyOnWriteArraySet<String> copySet = new CopyOnWriteArraySet<>();
    }
}
