package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.data.ScoreDaoImpl;
import com.example.aircraftwar2024.data.ScoreData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordActivity extends AppCompatActivity {
    private int gameType=0;
    private TextView textView;
    private ListView listView;
    private ScoreDaoImpl scoreDaoImpl=null;
    private String textContent=null;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager activityManager=ActivityManager.getActivityManager();
        activityManager.addActivity(this);
        setContentView(R.layout.activity_record);
        textView=findViewById(R.id.text_view);
        listView=(ListView)findViewById(R.id.list_view);
        Button btn1=(Button) findViewById(R.id.ExitButton);
        btn1.setOnClickListener(new ClickEvent());
        ScoreData scoreData=new ScoreData("0,error,0,1970-10-01 12:00:00");
        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",1);
            scoreData = new ScoreData(getIntent().getStringExtra("data"));
        }
        switch(gameType){
            case 0:
                textContent="简单模式排行榜";
                path="EasyRank.dat";
                break;
            case 1:
                textContent="普通模式排行榜";
                path="MediumRank.dat";
                break;
            default:
                textContent="困难模式排行榜";
                path="HardRank.dat";
                break;
        }
        try {
            scoreDaoImpl=new ScoreDaoImpl(this,path);
        } catch (IOException|ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            scoreDaoImpl.addData(scoreData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            scoreDaoImpl.writeToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            dataShow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(RecordActivity.this)
                        .setTitle("提示")
                        .setMessage("是否要删掉该条记录?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        try {
                                            scoreDaoImpl.deleteData(position);
                                            dataShow();
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                })
                        .setNegativeButton("取消",((dialogInterface, i) ->{}))
                        .create();
                alertDialog.show();
            }
        });
    }
    private void dataShow() throws IOException {
        scoreDaoImpl.sortByScore();
        String[][] array = scoreDaoImpl.getDisplayArray();
        ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i=0;i<array.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("排名",array[i][0]);
            map.put("玩家名",array[i][1]);
            map.put("分数",array[i][2]);
            map.put("记录时间",array[i][3]);
            listitem.add(map);
        }
        textView.setText(textContent);
        SimpleAdapter listItemAdapter = new SimpleAdapter(
                RecordActivity.this,
                listitem,
                R.layout.activity_item,
                new String[]{"排名","玩家名","分数","记录时间"},
                new int[]{R.id.rank,R.id.player,R.id.score,R.id.time});

        listView.setAdapter(listItemAdapter);
    }
    class ClickEvent implements View.OnClickListener{
        public void onClick(View v){
            Intent intent = new Intent(RecordActivity.this, MainActivity.class);
            ActivityManager activityManager=ActivityManager.getActivityManager();
            activityManager.finishAllActivity();
            startActivity(intent);
        }
    }
}