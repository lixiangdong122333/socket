/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package cn.tedu.vip.socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 聊天室服务端
 */
public class Server {
    /**
     * java.net.ServerSocket
     * 运行在服务端的ServerSocket主要有两个作用:
     * 1:向系统申请固定的服务端口，客户端Socket就是通过这个端口进行连接的
     * 2:监听服务端口，一旦一个客户端连接就会立即返回一个Socket，通过它与客户端
     *   进行双向交互。
     *
     * 如果我们将Socket比喻为"电话",那么ServerSocket相当于"总机"
     */
    private ServerSocket serverSocket;

    public Server(){
        try {
            /*
                实例化ServerSocket时要指定服务端口，该端口不能与系统其它应用程序
                占用的端口相同，否则会抛出异常:
                java.net.BindException:address already in use
                若出现该异常，解决办法为更换端口，直到不抛异常。
             */
            System.out.println("正在启动服务端...");
            serverSocket = new ServerSocket(8088);
            System.out.println("服务端启动完毕!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start(){
        try {
            while(true) {
                System.out.println("等待客户端连接...");
                /*
                    ServerSocket提供的方法:
                    Socket accept()
                    该方法是一个阻塞方法，调用后开始等待客户端的连接，一旦一个客户端
                    连接那么该方法会立即返回一个Socket。

                    好比这个方法总机这边的接电话动作
                 */
                Socket socket = serverSocket.accept();
                System.out.println("一个客户端连接了!");
                //启动一个线程与客户端交互
                ClientHandler handler=new ClientHandler(socket);
                Thread t=new Thread(handler);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }


    /**
     * 该线程用于与指定客户端通信
     */
    private class ClientHandler implements Runnable{
        private Socket socket;
        private String host;
        public ClientHandler(Socket socket) {
            this.socket=socket;
            //获取远端客户端的IP地址
            host=socket.getInetAddress().getHostAddress();
        }

        @Override
        public void run() {
            try {


                /*
                    Socket提供的方法:
                    InputStream getInputStream()
                    通过Socket获取的输入流可以读取远端计算机发送过来的数据
                 */
                InputStream in = socket.getInputStream();
                //转换流(高级流，字符流)。1:衔接字符与字节流  2:将读取的字节转换为字符
                InputStreamReader isr = new InputStreamReader(in,"utf-8");
                //缓冲流(高级流，字符流)。块读文本数据加速。按行读取字符串
                BufferedReader br = new BufferedReader(isr);

                //读取客户端发送的一行字符串
                String message;
                /*
                    服务端通过缓冲流读取客户端发送过来一行字符串的操作时，这个方法
                    会产生阻塞，等待对方发送消息，直到对方发送过来一行字符串则该方法
                    返回此行内容。
                    当客户端调用socket.close()断开连接，那么这里readLine方法会
                    返回null，表述流读取到了末尾(对方断开了连接)。
                    如果客户端是意外中断(强行关闭客户端程序，停电，断网等)那么服务端
                    这边readLine方法会抛出异常
                 */
                while ((message = br.readLine()) != null) {
                    System.out.println(host+"说:" + message);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
