package com.example.aircraftwar2024.data;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreDaoImpl implements ScoreDao{
    private List<ScoreData> scoreDatas;
    private Context context;
    private String path;
    public ScoreDaoImpl(Context context,String path) throws IOException, ClassNotFoundException {
        scoreDatas=new ArrayList<ScoreData>();
        this.context=context;
        this.path=path;
        File dataFile = new File(context.getFilesDir(), path);
        if (dataFile.exists()) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile));
            this.scoreDatas = (List<ScoreData>) ois.readObject();
        } else {
            this.scoreDatas = new ArrayList<>();
            writeToFile();
        }
    }
    @Override
    public void sortByScore() throws IOException {
        // 按照分数排序
        Collections.sort(scoreDatas, (a, b)->{return b.getScore() - a.getScore();});
        int i=1;
        for (ScoreData data:scoreDatas){
            data.setRank(i);
            i++;
        }
        // 重写进文件
        writeToFile();
    }
    @Override
    public void writeToFile() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(path, Context.MODE_PRIVATE));
        oos.writeObject(scoreDatas);
        oos.close();
    }
    @Override
    public void addData(ScoreData player) throws IOException {
        scoreDatas.add(player);
        sortByScore();
    }
    @Override
    public void deleteData(int index) throws IOException {
        scoreDatas.remove(index);
        writeToFile();
    }
    @Override
    public List<ScoreData> getAllData(){
        return scoreDatas;
    }
    public String[][] getDisplayArray(){
        String[][] array = new String[scoreDatas.size()][4];
        for(int i = 0; i < scoreDatas.size();i++){
            array[i] = scoreDatas.get(i).toStringArray();
        }
        return array;
    }
}
