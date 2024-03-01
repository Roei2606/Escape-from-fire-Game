package com.example.a24a10357roeihakmon206387128_task1.Utilities;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.a24a10357roeihakmon206387128_task1.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BackGroundSound  {
    private Context context;
    private Executor executor;
    private Handler handler;
    private MediaPlayer mediaPlayer;
    private int resourceID;

    public BackGroundSound(Context context, int resourceID ) {
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
        this.handler = new Handler(Looper.getMainLooper());
        this.resourceID = resourceID;
    }
    public void playSound(){
        executor.execute(()->{
            mediaPlayer = MediaPlayer.create(context, resourceID);
            mediaPlayer.setLooping(false);
            mediaPlayer.setVolume(1.0f, 1.0f);
            mediaPlayer.start();
        });
    }
    public void stopSound(){
        if (mediaPlayer != null) {
            executor.execute(() -> {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            });
        }
    }
}
