package com.cq.io.io_02_socket_bio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author chenquan
 * @Date 2022-11-10 23:44
 * @Description: TODO
 * @Version: 1.0
 **/

public class SocketBIO {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9090);
        System.out.println("serverSocket已启动!");
        System.in.read();
        Socket client = serverSocket.accept();
        System.out.println("serverSocket accept!");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream in = client.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    while (true){
                        String line = reader.readLine();
                        if(line != null){
                            System.out.println(line);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }
}
