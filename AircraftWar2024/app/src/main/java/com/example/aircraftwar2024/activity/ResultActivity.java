package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aircraftwar2024.R;

public class ResultActivity extends AppCompatActivity {

    private Integer score;
    private Integer enemyScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager activityManager=ActivityManager.getActivityManager();
        activityManager.addActivity(this);
        setContentView(R.layout.activity_result);

        Button btn = (Button) findViewById(R.id.ExitMainButton);
        btn.setOnClickListener(new ClickEvent());

        if(getIntent() != null){
            score = getIntent().getIntExtra("myScore", 0);
            enemyScore = getIntent().getIntExtra("enemyScore", 0);
        }

        TextView scoreView = findViewById(R.id.score_view);
        TextView enemyScoreView = findViewById(R.id.enemyscore_view);

        scoreView.setText("你的分数：" + score.toString());
        enemyScoreView.setText("对手分数: " + enemyScore.toString());
    }

    class ClickEvent implements View.OnClickListener{
        public void onClick(View v){
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            ActivityManager activityManager=ActivityManager.getActivityManager();
            activityManager.finishAllActivity();
            startActivity(intent);
        }
    }
}