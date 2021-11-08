/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.lixiangdong;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * 聊天室客户端
 */
public class Client {
    Socket socket;
    /**
     * 初始化Socket，连接至服务器
     */
    public Client() {
        try {
            System.out.println("正在连接......");
            socket=new Socket("127.0.0.1",8088);
            System.out.println("连接成功");
        }catch (IOException e){
            System.out.println("连接失败");
            e.printStackTrace();
        }
    }
    public void client(){
        try {
            OutputStream os=socket.getOutputStream();
            OutputStreamWriter ow=new OutputStreamWriter(os,"utf-8");
            BufferedWriter bw=new BufferedWriter(ow);
            PrintWriter pw=new PrintWriter(bw,true);

            InputStream is=socket.getInputStream();
            InputStreamReader ir=new InputStreamReader(is);
            BufferedReader bf=new BufferedReader(ir);


            Scanner scanner=new Scanner(System.in);
            for (;;){
                String date=scanner.nextLine();
                if (date.equals("exit"))
                    break;
                pw.println(date);
                System.out.println(bf.readLine());
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Client client=new Client();
        client.client();
    }
}
