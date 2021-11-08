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

    public Client() {
        try {
            System.out.println("开始连接.......");
            socket = new Socket("127.0.0.1", 8088);
            System.out.println("连接成功");
        } catch (Exception e) {
            System.out.println("连接失败");
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            ServerHandler handler=new ServerHandler();
            Thread t=new Thread(handler);
            t.start();


            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw, true);



            /*
            pw.println("测试");
             */
            Scanner scan = new Scanner(System.in);
            while (true) {
                String msg = scan.nextLine();
                if (msg.equals("exit"))
                    break;
                pw.println(msg);


            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("关流失败");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    private class ServerHandler implements Runnable {

        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String message;
                while ((message = br.readLine()) != null)
                    System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
