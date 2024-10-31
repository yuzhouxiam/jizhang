package com.hui.tally;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import com.hui.tally.R;

public class MusicService extends Service {
    MediaPlayer mp1;

    // 记录当前正在播放的音乐

    static int current=1;
public static void setCurrent(int c){
    current=c;
}
    @Override
    public void onCreate() {
        //开始服务时调用
        super.onCreate();
        if(current==1) {
            mp1 = MediaPlayer.create(this, R.raw.hsggx);
            mp1.start();
        }
        else if(current==2) {
            mp1 = MediaPlayer.create(this, R.raw.wwhm);
            mp1.start();
        }
        else if(current==3) {
            mp1 = MediaPlayer.create(this, R.raw.ytyt);
            mp1.start();
        }
        else if(current==4) {
            mp1 = MediaPlayer.create(this, R.raw.xrmc);
            mp1.start();
        }
        else if(current==5) {
            mp1 = MediaPlayer.create(this, R.raw.yhc);
            mp1.start();
        }
        else{
            Toast.makeText(this, "当前无正播放音乐", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp1.stop();
        if (mp1 != null) mp1=null;
    }

    @Override
    public IBinder onBind(Intent intent) {           //不可省略的生命周期方法
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
;

