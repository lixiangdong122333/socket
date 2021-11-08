package cn.tedu.vip.socket;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 聊天室客户端
 */
public class Client {
    private Socket socket;
    public Client(){
        try {
            System.out.println("开始连接.......");
            socket = new Socket("176.108.5.14", 8088);
            System.out.println("连接成功");
        }catch (Exception e){
            System.out.println("连接失败");
            e.printStackTrace();
        }
    }
    public void start(){
        try {
            OutputStream os=socket.getOutputStream();
            OutputStreamWriter osw=new OutputStreamWriter(os, StandardCharsets.UTF_8);
            BufferedWriter bw=new BufferedWriter(osw);
            PrintWriter pw=new PrintWriter(bw,true);
            /*
            pw.println("测试");
             */
            Scanner scan=new Scanner(System.in);
            while (true){
                String msg=scan.nextLine();
                if (msg.equals("exit"))
                    break;
                pw.println(msg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("关流失败");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Client client=new Client();
        client.start();
    }
}
