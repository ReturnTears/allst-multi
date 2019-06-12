package com.allst.multi.thread4;

/**
 * @author June
 * @version 1.0
 * @date 2018-07-01
 */
public class Main {

    public static void main(String[] args) {
        FutureClient client = new FutureClient();
        MyData data = client.request("请求参数");
        System.out.println("请求发送成功！");
        System.out.println("做其他的事情...");

        String result = data.getRequest();
        System.out.println(result);
    }
}
