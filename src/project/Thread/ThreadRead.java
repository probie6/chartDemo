package project.Thread;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ThreadRead implements Runnable{
    private Socket socket;
    private String host;

    public ThreadRead(Socket socket) {
        this.socket = socket;
        //获取远端客户端ip地址
        InetAddress address = socket.getInetAddress();
        host = address.getHostAddress();
    }
    @Override
    public void run() {
        try {
            /**
             * Socket提供的方法
             * inputStream getInputStream()
             * 该方法可以获取一个输入流，从该流读取的数据就是从远端计算机发送过来的
             */
            InputStream  inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String message = null;

//            Scanner scanner = new Scanner(System.in);
//            OutputStream outputStream = socket.getOutputStream();
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
//            PrintWriter printWriter = new PrintWriter(outputStreamWriter,true);
            while((message = bufferedReader.readLine()) != null) {
                System.out.println(host+"说："+message);
            }

        } catch (IOException e) {

        }finally {
            /**
             * 处理当前客户端断开的逻辑
             */
            System.out.println(host+"下线了");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
