package com.hui.tally.utils;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.hui.tally.AboutActivity;
import com.hui.tally.ExchangeRateConversionActivity;
import com.hui.tally.HistoryActivity;
import com.hui.tally.MainActivity;
import com.hui.tally.MonthChartActivity;
import com.hui.tally.PlayMusic;
import com.hui.tally.R;
import com.hui.tally.SettingActivity;

public class MoreDialog extends Dialog implements View.OnClickListener {
    Button aboutBtn,settingBtn,historyBtn,infoBtn,exchangeBtn,musicBtn;
    ImageView errorIv;
    public MoreDialog(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_more);
        aboutBtn = findViewById(R.id.dialog_more_btn_about);
        settingBtn = findViewById(R.id.dialog_more_btn_setting);
        historyBtn = findViewById(R.id.dialog_more_btn_record);
        infoBtn = findViewById(R.id.dialog_more_btn_info);
        exchangeBtn = findViewById(R.id.dialog_exchange_btn);
        errorIv = findViewById(R.id.dialog_more_iv);
        musicBtn=findViewById(R.id.dialog_close_music);

        aboutBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
        infoBtn.setOnClickListener(this);
        exchangeBtn.setOnClickListener(this);
        errorIv.setOnClickListener(this);
        musicBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if(v.getId()==R.id.dialog_more_btn_about) {
            intent.setClass(getContext(), AboutActivity.class);
            getContext().startActivity(intent);
        }
        else if(v.getId()==R.id.dialog_more_btn_setting){
            intent.setClass(getContext(), SettingActivity.class);
            getContext().startActivity(intent);
        }
        else if(v.getId()==R.id.dialog_more_btn_record) {
            intent.setClass(getContext(), HistoryActivity.class);
            getContext().startActivity(intent);
        }
        else if (v.getId()==R.id.dialog_more_btn_info) {
            intent.setClass(getContext(), MonthChartActivity.class);
            getContext().startActivity(intent);
        }
        else if(v.getId()==R.id.dialog_more_iv){
            intent.setClass(getContext(), MainActivity.class);
            getContext().startActivity(intent);
        }
        else if(v.getId()==R.id.dialog_exchange_btn){
            intent.setClass(getContext(), ExchangeRateConversionActivity.class);
            getContext().startActivity(intent);
        }
        else if(v.getId()==R.id.dialog_close_music){
            intent.setClass(getContext(), PlayMusic.class);
            getContext().startActivity(intent);
        }
        cancel();
    }


    /* 设置Dialog的尺寸和屏幕尺寸一致*/
    public void setDialogSize(){
//        获取当前窗口对象
        Window window = getWindow();
//        获取窗口对象的参数
        WindowManager.LayoutParams wlp = window.getAttributes();
//        获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int)(d.getWidth());  //对话框窗口为屏幕窗口
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }
}
