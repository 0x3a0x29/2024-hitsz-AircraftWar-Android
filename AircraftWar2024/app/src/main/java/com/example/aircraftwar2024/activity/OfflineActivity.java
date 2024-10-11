package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aircraftwar2024.R;

public class OfflineActivity extends AppCompatActivity {
    protected boolean music=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager activityManager=ActivityManager.getActivityManager();
        activityManager.addActivity(this);
        setContentView(R.layout.activity_offline);
        if(getIntent() != null) {
            music = getIntent().getBooleanExtra("music", false);
        }
        System.out.print("Music in OfflineActivity:");
        System.out.println(music);
        Button btn1=(Button) findViewById(R.id.EasyButton);
        btn1.setOnClickListener(new OfflineActivity.ClickEvent());
        Button btn2=(Button) findViewById(R.id.NormalButton);
        btn2.setOnClickListener(new OfflineActivity.ClickEvent());
        Button btn3=(Button) findViewById(R.id.HardButton);
        btn3.setOnClickListener(new OfflineActivity.ClickEvent());
    }
    class ClickEvent implements View.OnClickListener{
        public void onClick(View v){
            Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
            intent.putExtra("music",music);
            if(v.getId()==R.id.EasyButton){intent.putExtra("gameType",0);}
            if (v.getId()==R.id.NormalButton){intent.putExtra("gameType",1);}
            if (v.getId()==R.id.HardButton){intent.putExtra("gameType",2);}
            startActivity(intent);
        }
    }
}