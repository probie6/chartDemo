package project.chart;

import project.Thread.ThreadWrite;
import project.Thread.ThreadRead;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 聊天室服务端
 * 基于TCP协议（核心类ServerSocket）
 * @author wangfei
 */
public class Server {
    /*
     * 运行在服务端的ServerSocket主要负责：
     * 1：向系统申请服务端口
     * 客户端就是通过这个端口与之连接的
     * 2：监听申请的服务端口，当一个客户端通过该端口尝试建立连接时
     * ServerSocket会在服务端创建一个Socket与客户端建立连接
     */
    private ServerSocket serverSocket;

    /*
     * 构造方法，用于初始化服务端
     */
    public Server() throws Exception{
        serverSocket = new ServerSocket(8081);
    }

    /*
     *用于启动服务端
     */
    private void start() {
        try {
            while(true) {
                System.out.println("等待连接......");
                /*
                 * accept方法是一个阻塞方法，作用是监听服务端口，直到一个客户端连接并创建一个Socket，
                 * 使用该Socket与刚连接的客户端交互
                 */
                Socket socket = serverSocket.accept();
                System.out.println(socket.getLocalAddress()+"客户端连接成功");

                ThreadPoolExecutor threadPoolExecutor =
                        new ThreadPoolExecutor(5,8,200, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(5));

                threadPoolExecutor.execute(new ThreadRead(socket));
                threadPoolExecutor.execute(new ThreadWrite(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        try{
            Server server = new Server();
            server.start();
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("服务端启动失败");
        }
    }
}
