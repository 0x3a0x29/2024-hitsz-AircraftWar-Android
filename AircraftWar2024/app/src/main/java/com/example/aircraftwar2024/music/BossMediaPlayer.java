package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.aircraftwar2024.R;

public class BossMediaPlayer {

    private MediaPlayer bossMP;
    public Context context;

    public BossMediaPlayer(Context context){
        this.context = context;
        bossMP = MediaPlayer.create(context, R.raw.bgm_boss);
    }

    public void loopRunBossMusic(){
        if(bossMP != null && !bossMP.isPlaying()){
            bossMP.setLooping(true);
            bossMP.start();
        }
    }

    public void stopBossMusic(){
        if(bossMP != null){
            bossMP.pause();
        }
    }
}
