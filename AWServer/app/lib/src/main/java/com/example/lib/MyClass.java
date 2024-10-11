package com.example.lib;

//import android.util.Log;



import java.net.ServerSocket;
import java.net.Socket;

public class MyClass {


    public static void main(String[] args){
        new MyClass();
    }

    public MyClass(){
        try {
            // 1. 创建ServerSocket
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("--Listener Port: 9999--");
            while (true) {
                //2.等待接收请求，这里接收客户端的请求
                Socket client1 = serverSocket.accept();
//                Log.i("Msg1","Receive1");
                Socket client2 = serverSocket.accept();
//                Log.i("Msg2","Receive2");

                //3.开启子线程线程处理和客户端的消息传输
                new ServerSocketThread(client1, client2).start();

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}