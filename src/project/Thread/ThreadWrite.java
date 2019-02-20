package project.Thread;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ThreadWrite implements Runnable {
    private Socket socket;
    private String host;

    public ThreadWrite(Socket socket) {
        this.socket = socket;
        //获取远端客户端ip地址
        InetAddress address = socket.getInetAddress();
        host = address.getHostAddress();
    }
    @Override
    public void run() {
        try{
            Scanner scanner = new Scanner(System.in);
            /**
             * OutputStream是一个抽象类，不能实例化。所以这个地方返回的是OutputStream的子类，
             * 具体是哪一个类不需要管。这样设计的目的就是让开发者不需要了解底层原理
             * OutputStream输出流，通过该流写出数据发送到服务端
             */
            OutputStream outputStream = socket.getOutputStream();

            //转换流，将字符流按指定字符集转换为字符流
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
            //true表示自动行刷新
            PrintWriter printWriter = new PrintWriter(outputStreamWriter,true);
            while(true) {
                printWriter.println(scanner.nextLine());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
