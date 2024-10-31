package com.hui.tally;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PlayMusic extends AppCompatActivity implements View.OnClickListener{
    String[] titleStrs = new String[] { "红色高跟鞋", "未闻花名", "一天一天" ,"夏日蝉鸣","萤火虫"};
    Intent intent;
    Button btn_play,btn_stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);
        btn_play = findViewById(R.id.btn_play);
        btn_stop = findViewById(R.id.btn_stop);
        btn_play.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        intent = new Intent(this, MusicService.class);    //用intent来实现服务的运行和停止
        int id = v.getId();
        TextView tv = findViewById(R.id.current_name);
        if(id==R.id.btn_play) {
            startService(intent);
            Toast.makeText(this, "播放音乐", Toast.LENGTH_SHORT).show();
            btn_stop.setEnabled(true);
            btn_play.setEnabled(false);
        }
            else if(id==R.id.btn_stop){
                stopService(intent);
            Toast.makeText(this, "关闭音乐", Toast.LENGTH_SHORT).show();
                btn_stop.setEnabled(false);
                btn_play.setEnabled(true);
        }

        else if(id==R.id.setting_music1){
            MusicService.setCurrent(1);
            tv.setText(titleStrs[0]);
            stopService(intent);
            startService(intent);
            Toast.makeText(this, "切换音乐至-红色高跟鞋", Toast.LENGTH_SHORT).show();
            btn_stop.setEnabled(true);
            btn_play.setEnabled(false);
        }
        else if(id==R.id.setting_music2){
            MusicService.setCurrent(2);
            tv.setText(titleStrs[1]);
            stopService(intent);
            startService(intent);
            Toast.makeText(this, "切换音乐至-未闻花名", Toast.LENGTH_SHORT).show();
            btn_stop.setEnabled(true);
            btn_play.setEnabled(false);
        }
        else if(id==R.id.setting_music3){
            MusicService.setCurrent(3);
            tv.setText(titleStrs[2]);
            stopService(intent);
            startService(intent);
            Toast.makeText(this, "切换音乐至-一天一天", Toast.LENGTH_SHORT).show();
            btn_stop.setEnabled(true);
            btn_play.setEnabled(false);
        }
        else if(id==R.id.setting_music4){
            MusicService.setCurrent(4);
            tv.setText(titleStrs[3]);
            stopService(intent);
            startService(intent);
            Toast.makeText(this, "切换音乐至-夏日蝉鸣", Toast.LENGTH_SHORT).show();
            btn_stop.setEnabled(true);
            btn_play.setEnabled(false);
        }
        else if(id==R.id.setting_music5){
            MusicService.setCurrent(5);
            tv.setText(titleStrs[4]);
            stopService(intent);
            startService(intent);
            Toast.makeText(this, "切换音乐至-萤火虫", Toast.LENGTH_SHORT).show();
            btn_stop.setEnabled(true);
            btn_play.setEnabled(false);
        }
        else if (id==R.id.chart_iv_back) {
            finish();
        }
    }

   // @Override
    //protected void onDestroy() {           //考虑播放时返回
      //  super.onDestroy();
       // if (intent != null) stopService(intent);   //停止服务
       // finish();                                 //关闭
    //}
}