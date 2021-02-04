package com.allst.netty.actual_combat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 阻塞I/O
 *
 * @author June
 * @since 2021年02月
 */
public class BlockIONet {
    public static void main(String[] args) {
        int portNumber = 6666;
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String request, response;
            while ((request = in.readLine()) != null) {
                if ("Done".equals(request)) {
                    break;
                }
                response = processRequest(request);
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processRequest(String request) {
        System.out.println("obtain client data is: " + request);
        return "server deal with result ~" + request;
    }
}
