package com.example.aircraftwar2024.game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;

import com.example.aircraftwar2024.activity.MainActivity;
import com.example.aircraftwar2024.data.ScoreData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class OnlineGame extends MediumGame{
    private int enemyScore;
    private Socket socket;

    public OnlineGame(Context context, Handler mHandler, boolean music,Socket socket) throws IOException {
        super(context, mHandler, music);
        this.socket=socket;
        new ClientThread().start();

    }
    public void setEnemyScore(int enemyScore){
        this.enemyScore=enemyScore;
    }
    @Override
    protected void gameOver(){
        gameOverFlag=true;
//        mbLoop = false;
        if(openMusic){
            myMediaPlayer.stopMusic();
            if(gameOverFlag){
                mySoundPool.startGameOverSound();
            }
        }
    }
    @Override
    protected void paintScoreAndLife() {
        super.paintScoreAndLife();
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(80);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        // 绘制敌方分数
        canvas.drawText("ENEMYSCORE: " + enemyScore, 20, 240, mPaint);
    }


    private class ClientThread extends Thread{
        private Socket socket = null;
        private PrintWriter writer = null;
        private BufferedReader in = null;
        ClientThread() throws IOException {}
        @Override
        public void run(){
            try {
                socket = MainActivity.socket;
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                writer = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream(),"utf-8")),true);
                new Thread(()->{
                    while (!gameOverFlag) {
                        try {
                            writer.println("" + getScore());
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    writer.println("end");
                }).start();
                new Thread(()->{
                    try {
                        String content;
                        while((content = in.readLine()) != null){
                            if (content.equals("end")){
                                ScoreData scoreData = new ScoreData("test",getScore(),new Date());
                                Message message = Message.obtain();
                                message.what = 1;
                                message.obj = scoreData;
                                message.arg1 = enemyScore;
                                mHandler.sendMessage(message);
                                mbLoop = false;
                                break;
                            }else {
                                enemyScore = Integer.parseInt(content);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    @Override
    public void run() {
        while (true) {
            draw();
            if(!gameOverFlag){
                action();
            }
        }
    }
}
