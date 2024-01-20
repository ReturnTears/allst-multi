package com.allst.async.chapter4;

import com.google.common.collect.Lists;

import java.util.List;

/**
 *
 *
 * @author Hutu
 * @since 2024-01-20 下午 05:46
 */
public class StreamFutureCode {
    public static void main(String[] args) {
        // 1、生成IP列表
        List<String> ipList = Lists.newArrayList();
        for (int i = 1; i < 11; i++) {
            ipList.add("192.168.0." + i);
        }
        // 2、发起广播调用
        long start = System.currentTimeMillis();
        List<String> result = Lists.newArrayList();
        for (String ip : ipList) {
            result.add(rpcCall(ip, ip));
        }
        // 3、输出
        result.forEach(System.out::println);
        System.out.println("Cost : " + (System.currentTimeMillis() - start));
    }

    private static String rpcCall(String ip, String param) {
        System.out.println(ip + " rpcCall : " + param);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return param;
    }
}
