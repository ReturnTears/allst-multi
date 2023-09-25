package com.allst.multi.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP/IP+BIO
 * <p>
 * 基于Socket的服务端代码
 *
 * @author Hutu
 * @since 2023-05-16 下午 07:46
 */
public class TcpipBioCommunicationServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(8086);
        System.out.println("server listen port is : 8086");
        Socket socket = ss.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        while (true) {
            String line = in.readLine();
            if (line == null) {
                Thread.sleep(100);
                continue;
            }
            if ("quit".equalsIgnoreCase(line.trim())) {
                in.close();
                out.close();
                ss.close();
                System.out.println("Server has been shutdown!");
                System.exit(0);
            } else {
                System.out.println("Message from client: " + line);
                out.println("Server response：" + line);
                Thread.sleep(100);
            }
        }
    }
}
