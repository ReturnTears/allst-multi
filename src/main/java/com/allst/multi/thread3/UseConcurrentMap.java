package com.allst.multi.thread3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author June
 * @version 1.0
 * @date 2018-06-30
 */
public class UseConcurrentMap {

    public static void main(String[] args) {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>(4);
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        map.putIfAbsent("k4", "v4");
        System.out.println("集合长度为 : " + map.size());
        for (Map.Entry<String, Object> me : map.entrySet()) {
            System.out.println("key : " + me.getKey() + " , value : " + me.getValue());
        }
    }
}
