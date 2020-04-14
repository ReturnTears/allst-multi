package com.allst.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIOServer
 * @author YiYa
 * @since 2020-04-14 下午 11:08
 */
public class BioServer {
    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动成功~");
        while (true) {
            System.out.printf("监听线程id: %s, 线程name: %s\n", Thread.currentThread().getId(), Thread.currentThread().getName());
            System.out.println("等待连接~服务启动成功，没有请求连接时会阻塞在这里~"); //
            final Socket accept = serverSocket.accept();
            System.out.println("一client连接到服务器~");
            service.execute(() -> handler(accept));
        }
    }

    private static void handler(Socket socket) {
        try {
            System.out.printf("通信线程id: %s, 线程name: %s\n", Thread.currentThread().getId(), Thread.currentThread().getName());
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            while (true) {
                System.out.printf("处理线程id: %s, 线程name: %s\n", Thread.currentThread().getId(), Thread.currentThread().getName());
                System.out.println("clients与服务器建立了连接， 没有消息时会阻塞在这里~");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("关闭和client的连接");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
