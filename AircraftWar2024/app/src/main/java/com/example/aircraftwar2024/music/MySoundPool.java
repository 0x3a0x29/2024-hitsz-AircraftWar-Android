package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.example.aircraftwar2024.R;

import java.util.HashMap;

public class MySoundPool {
    private SoundPool mysp;
    private AudioAttributes audioAttributes = null;
    private HashMap<Integer, Integer> soundPoolMap = new HashMap<>();

    public MySoundPool(Context context){
        audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        mysp = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(audioAttributes)
                .build();
        soundPoolMap.put(R.raw.bullet_hit, mysp.load(context, R.raw.bullet_hit,1));
        soundPoolMap.put(R.raw.bomb_explosion, mysp.load(context, R.raw.bomb_explosion,1));
        soundPoolMap.put(R.raw.get_supply, mysp.load(context, R.raw.get_supply,1));
        soundPoolMap.put(R.raw.game_over, mysp.load(context, R.raw.game_over,1));
    }

    public void startBulletHitSound() {
        mysp.play(soundPoolMap.get(R.raw.bullet_hit), 1,1,0,0,1.2f);
    }

    public void startBombExplosion() {
        mysp.play(soundPoolMap.get(R.raw.bomb_explosion), 1,1,0,0,1.2f);
    }

    public void startGetSupplySound() {
        mysp.play(soundPoolMap.get(R.raw.get_supply), 1,1,0,0,1.2f);
    }

    public void startGameOverSound() {
        mysp.play(soundPoolMap.get(R.raw.game_over), 1,1,0,0,1.2f);
    }

}
