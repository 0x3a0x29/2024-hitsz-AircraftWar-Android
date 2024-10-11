package com.example.aircraftwar2024.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.example.aircraftwar2024.data.ScoreData;
import com.example.aircraftwar2024.game.OnlineGame;

import java.io.IOException;


public class OnlineActivity extends GameActivity{
    private Handler handler;
    private boolean music=false;
    private OnlineGame gameView;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager activityManager=ActivityManager.getActivityManager();
        activityManager.addActivity(this);
        handler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if (msg.what==1){
                    try {
                        MainActivity.socket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Intent intent = new Intent(OnlineActivity.this, ResultActivity.class);
                    intent.putExtra("music",music);
//                    Intent intent = new Intent(OnlineActivity.this, MainActivity.class);
                    ScoreData scoreData = (ScoreData) msg.obj;
                    intent.putExtra("myScore", scoreData.getScore());
                    intent.putExtra("enemyScore", msg.arg1);
                    Toast.makeText(OnlineActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        };
        if (getIntent() != null) {
            music = getIntent().getBooleanExtra("music", false);
        }
        try {
            gameView = new OnlineGame(this,handler,music,MainActivity.socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setContentView(gameView);
    }
}
