package com.example.smd_final_project;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MyBoundService extends Service {

    public static final String TAG = "TAG";
    private final Binder myBinder = new MyBinder();
    public MediaPlayer mediaPlayer;

    String audiourl;

    public void setAudiourl(String audiourl) {
        this.audiourl = audiourl;
        Log.d(TAG,"bind audio"+this.audiourl);
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        mediaPlayer = MediaPlayer.create(this, R.raw.surah);
          mediaPlayer = new MediaPlayer();
        try {
            if(audiourl!=null){
                mediaPlayer.setDataSource(audiourl);
                mediaPlayer.prepare();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Play(){
        try {
            if(audiourl!=null){
                mediaPlayer.setDataSource(audiourl);
                mediaPlayer.prepare();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mediaPlayer.start();
    }
    public void Pause(){
        mediaPlayer.pause();
    }

    public boolean isPlay(){
        return mediaPlayer.isPlaying();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"OnBind() started...");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onunbind() is called...");
        return super.onUnbind(intent);
    }

    public class MyBinder  extends Binder {
        MyBoundService getServiceMethod()
        {
            return MyBoundService.this;
        }
    }
}
