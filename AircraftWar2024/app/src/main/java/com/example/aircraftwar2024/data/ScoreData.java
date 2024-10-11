package com.example.aircraftwar2024.data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreData implements Serializable {
    private String name="test";
    private static final long serialVersionUID = 1L;
    private int rank=0;
    private int score=0;
    private Date time;
    public ScoreData(String name,int score,Date time){
        this.rank=rank;
        this.name=name;
        this.score=score;
        this.time=time;
    }
    public ScoreData(String s){
        String[] string=s.split(",");
        this.rank=Integer.parseInt(string[0]);
        this.name=string[1];
        this.score=Integer.parseInt(string[2]);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.time= sdf.parse(string[3]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public int getScore(){
        return score;
    }
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return rank+","+name+","+score+","+sdf.format(time);
    }
    public String[] toStringArray(){
        String[] str;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        str=new String[]{String.valueOf(rank),name,String.valueOf(score),sdf.format(time)};
        return str;
    }
    public void setRank(int rank){
        this.rank=rank;
    }
}
