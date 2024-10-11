package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.OnlineGame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    protected boolean music=false;
    public static Socket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1=(Button) findViewById(R.id.StartButton);
        btn1.setOnClickListener(new ClickEvent());
        Button btn2=(Button) findViewById(R.id.radio_on);
        btn2.setOnClickListener(new ClickEvent());
        Button btn3=(Button) findViewById(R.id.radio_off);
        btn3.setOnClickListener(new ClickEvent());
        Button btn4=(Button) findViewById(R.id.OnlineButton);
        btn4.setOnClickListener(new ClickEvent());
        if (getIntent() != null) {
            int myScore = getIntent().getIntExtra("myScore", -1);
            int enemyScore = getIntent().getIntExtra("enemyScore", -1);
            if(myScore != -1) {
                Dialog alertDialog = new AlertDialog.Builder(this).setMessage("你的分数:" + myScore + "\n对手分数:" + enemyScore).create();
                alertDialog.show();
            }
        }
    }
    class ClickEvent implements View.OnClickListener{
        public void onClick(View v){
            if(v.getId()==R.id.StartButton){
                Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
                intent.putExtra("music",music);
                startActivity(intent);
            }
            if(v.getId()==R.id.OnlineButton){
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("正在联机匹配中...")
                        .setCancelable(false)
                        .create();
                alertDialog.show();
                ClientThread clientThread = new ClientThread(alertDialog);
                clientThread.start();
            }
            if (v.getId()==R.id.radio_on){music=true;}
            if (v.getId()==R.id.radio_off){music=false;}
        }
    }
    protected class ClientThread extends Thread {
        private AlertDialog alertDialog;
        private PrintWriter writer=null;
        private BufferedReader in=null;
        private String HOST = "10.0.2.2";
        private int PORT = 9999;
        private int myScore;
        private OnlineGame game = null;

        public ClientThread(AlertDialog alertDialog) {
            this.alertDialog = alertDialog;
        }

        @Override
        public void run() {
            try {
                socket = new Socket(HOST,PORT);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                writer = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream(),"utf-8")),true);
                if (in.readLine().equals("start")){
                    alertDialog.dismiss();
                    Intent intent = new Intent(MainActivity.this, OnlineActivity.class);
                    intent.putExtra("music",music);
                    startActivity(intent);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}