package com.allst.multi.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * TCP/IP+BIO
 *
 * 基于Socket的客户端代码
 *
 * @author Hutu
 * @since 2023-05-16 下午 07:27
 */
public class TcpipBioCommunicationClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8086);
        // 创建读取服务端返回流的BufferedReader
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // 创建向服务端写入流的PrintWriter
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        boolean flag = true;
        while (flag) {
            String command = systemIn.readLine();
            if (command == null || "quit".equalsIgnoreCase(command.trim())) {
                flag = false;
                System.out.println("Client Quit！~");
                out.println("Quit");
                out.close();
                in.close();
                socket.close();
                continue;
            }
            out.println(command);
            String response = in.readLine();
            System.out.println("Message from server: " + response);
        }
    }
}
