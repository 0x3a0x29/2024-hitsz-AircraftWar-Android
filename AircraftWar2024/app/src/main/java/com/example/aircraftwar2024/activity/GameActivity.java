package com.example.aircraftwar2024.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.data.ScoreData;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;


public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    private int gameType=0;
    private boolean music=false;
    public static int screenWidth,screenHeight;
    public Handler mHandler = new Handler(Looper.getMainLooper()){
        public void handleMessage(Message msg){
            if (msg.what==0){
                ScoreData scoreData=(ScoreData) msg.obj;
                Intent intent = new Intent(GameActivity.this, RecordActivity.class);
                intent.putExtra("gameType",gameType);
                intent.putExtra("music",music);
                intent.putExtra("data",scoreData.toString());
                startActivity(intent);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager activityManager=ActivityManager.getActivityManager();
        activityManager.addActivity(this);
        getScreenHW();
        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",1);
            music = getIntent().getBooleanExtra("music",false);
            System.out.print("Music in GameActivity:");
            System.out.println(music);
            System.out.print("GameType in GameActivity:");
            System.out.println(gameType);
        }
        BaseGame baseGameView = null;
        switch(gameType){
            case 0:
                baseGameView = new EasyGame(this,mHandler,music);
                break;
            case 1:
                baseGameView = new MediumGame(this,mHandler,music);
                break;
            default:
                baseGameView = new HardGame(this,mHandler,music);
                break;
        }
        setContentView(baseGameView);
    }

    public void getScreenHW(){
        //定义DisplayMetrics 对象
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getDisplay().getRealMetrics(dm);

        //窗口的宽度
        screenWidth= dm.widthPixels;
        //窗口高度
        screenHeight = dm.heightPixels;

        Log.i(TAG, "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}