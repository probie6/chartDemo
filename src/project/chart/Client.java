package project.chart;

import project.Thread.ThreadWrite;
import project.Thread.ThreadRead;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 聊天室客户端
 * 基于TCP协议（核心类Socket）
 * @author wangfei
 */
public class Client {
    /*
     *套接字
     *java.net.Socket
     * 封装了TCP协议，使用他就可以基于TCP协议进行网络通讯
     * Socket是运行在客户端的
     */
    private Socket socket;

    /**
     * 构造方法 用于初始化客户端
     * @throws Exception
     */
    public Client() throws Exception {
        System.out.println("正在连接服务端");
        /**
         * 第一个参数为远程计算机的ip地址（通过ip找到计算机）
         * 第二个参数为远程计算机的端口。（通过端口找到对应程序）
         * 实例化socket就是远程连接计算机过程，若远程服务端没有启动，则会抛出异常
         *
         */

        socket = new Socket("192.168.2.176",8081);
        System.out.println("服务端连接成功");

    }

    /**
     * 启动客户端
     */
    public void start() {
        try{
            ThreadPoolExecutor threadPoolExecutor =
                    new ThreadPoolExecutor(5,8,200, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(5));

            threadPoolExecutor.execute(new ThreadWrite(socket));
            threadPoolExecutor.execute(new ThreadRead(socket));
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        try {
            Client client = new Client();
            client.start();
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("客户端启动失败");
        }

    }

 }