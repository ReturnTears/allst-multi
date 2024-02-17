package com.allst.async.chapter_reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Hutu
 * @since 2024-02-17 下午 11:00
 */
public class ReactorAsyncRpcCall {
    public static void main(String[] args) throws InterruptedException {
// 1.生成ip列表
        List<String> ipList = new ArrayList<>();
        for (int i = 1; i <= 10; ++i) {
            ipList.add("192.168.0." + i);
        }

        // 2.并发调用
        Flux.fromArray(ipList.toArray(new String[0])).flatMap(ip -> // 2.1
                        Flux.just(ip)// 2.2
                                .subscribeOn(Schedulers.boundedElastic())// 2.3
                                .map(v -> rpcCall(v, v)))// 2.4
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String t) {
                        System.out.println("accept : " + t);
                    }
                });
        Thread.sleep(3000);
    }

    private static String rpcCall(String ip, String param) {
        System.out.println(ip + " rpcCall:" + param);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ip : " + param;
    }

}
