package com.allst.async.chapter4;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 借用Stream和CompletableFuture对业务线程并发地发起多次rpc请求
 *
 * @author Hutu
 * @since 2024-01-20 下午 05:46
 */
public class StreamCompletableFutureCode {
    public static void main(String[] args) {
        // 1、生成IP列表
        List<String> ipList = Lists.newArrayList();
        for (int i = 1; i < 11; i++) {
            ipList.add("192.168.0." + i);
        }
        // 2、并发调用
        long start = System.currentTimeMillis();
        List<CompletableFuture<String>> futureList = ipList.stream()
                .map(ip -> CompletableFuture.supplyAsync(() -> rpcCall(ip, ip))) // 同步转换为异步
                .collect(Collectors.toList()); // 收集结果
        // 等待所有异步任务执行完毕
        List<String> resultList = futureList.stream().map(CompletableFuture::join) // 同步等待结果
                .collect(Collectors.toList()); // 对结果进行收集
        // 4、输出
        resultList.forEach(System.out::println);
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
