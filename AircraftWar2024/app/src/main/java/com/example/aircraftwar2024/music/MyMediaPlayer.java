package com.example.aircraftwar2024.music;

import android.media.MediaPlayer;
import android.content.Context;

import com.example.aircraftwar2024.R;


public class MyMediaPlayer {
    private MediaPlayer bgMP;
    public Context context;

    public MyMediaPlayer(Context context){
        this.context = context;
        bgMP = MediaPlayer.create(context, R.raw.bgm);
    }

    public void loopRunMusic(){
        if(bgMP != null && !bgMP.isPlaying()){
            bgMP.setLooping(true);
            bgMP.start();
        }
    }

    public void pauseMusic(){
        if(bgMP != null && bgMP.isPlaying()){
//            int position = bgMP.getCurrentPosition();
//            bgMP.seekTo(position);
//            bgMP.start();
            bgMP.pause();
        }
    }

    public void stopMusic(){
        if(bgMP != null){
            bgMP.stop();
            bgMP.release();
            bgMP=null;
        }
    }

    public void resumeMusic(){
        if(bgMP != null && !bgMP.isPlaying()){
            bgMP.start();
        }
    }

}