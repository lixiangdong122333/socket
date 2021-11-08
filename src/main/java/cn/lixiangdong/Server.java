/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.lixiangdong;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket;
    public Server() {
        try {
            System.out.println("正在准备监听端口.....");
            serverSocket=new ServerSocket(8088);
            System.out.println("正在监听端口");

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void start(){
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                socket.getInetAddress();
                System.out.println(socket.getInetAddress()+"接入");
                new Thread(new Implement(socket)).start();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Server server=new Server();
        server.start();
    }
    class Implement implements Runnable{
        Socket socket;
        public Implement(Socket socket) {
            this.socket=socket;
        }
        @Override
        public void run() {
            try {
                InputStream is=socket.getInputStream();
                InputStreamReader ir=new InputStreamReader(is);
                BufferedReader bf=new BufferedReader(ir);
                while (true)
                    System.out.println(bf.readLine());
            } catch (IOException e) {
                System.out.println(socket.getInetAddress()+"已断开");
            }
        }
    }
}
