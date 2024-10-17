package com.allst.concurrent.future;

/**
 * @author Hutu
 * @since 2024-10-17 下午 09:28
 */
public class AppFuture {
    public static void main(String[] args) {
        Client client = new Client();
        Data data = client.request("request param");
        System.out.println("send success~");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = data.getResult();
        System.out.println("realData : " + result);
    }
}
