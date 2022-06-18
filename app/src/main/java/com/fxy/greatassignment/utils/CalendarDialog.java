package com.fxy.greatassignment.utils;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.fxy.greatassignment.R;

import java.text.SimpleDateFormat;

/*
 * 选择时间对话框
 */
public class CalendarDialog extends Dialog implements View.OnClickListener {
    // 定义所需要控件
    DatePicker datePicker;
    EditText hour,minute;
    Button cancel,ensure;

    // 设置点击确定接口 与 备注类似
    OnEnsureListener onEnsureListener;
    public  interface OnEnsureListener{
        public void onEnsure(String time, int year, int month, int day);
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取calender 的 xml
        setContentView(R.layout.dialog_calendar);

        // 绑定对应id
        datePicker = findViewById(R.id.dialog_calendar_dp);
        hour = findViewById(R.id.dialog_calendar_et_hour);
        minute = findViewById(R.id.dialog_calendar_et_minute);
        cancel = findViewById(R.id.dialog_calendar_btn_cancel);
        ensure = findViewById(R.id.dialog_calendar_btn_ensure);

        // 绑定确认与取消的点击事件
        ensure.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    public CalendarDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_calendar_btn_cancel:
                cancel();//取消并返回
                break;
            case R.id.dialog_calendar_btn_ensure:
                //获取年月日
                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();

                //获取输入的小时和分钟
                String hourStr = hour.getText().toString();
                String minuteStr = minute.getText().toString();


                // 处理年月日的格式问题
                String monthStr = String.valueOf(month);
                if (month<10){
                    monthStr = "0"+month;
                }
                String dayStr = String.valueOf(day);
                if (day<10){
                    dayStr="0"+day;
                }


                // 处理时间格式化
                int hour = 0;
                //不满足24小时  或者60分钟的将其取余数
                if (!TextUtils.isEmpty(hourStr)) {
                    hour = Integer.parseInt(hourStr);
                    hour=hour%24;
                }
                int minute = 0;
                if (!TextUtils.isEmpty(minuteStr)) {
                    minute = Integer.parseInt(minuteStr);
                    minute=minute%60;
                }
                // 处理完数据转为str
                hourStr=String.valueOf(hour);
                minuteStr=String.valueOf(minute);
                // 格式化输入时间
                if (hour<10){
                    hourStr="0"+hour;
                }
                if (minute<10){
                    minuteStr="0"+minute;
                }
                String time = year+"年"+monthStr+"月"+dayStr+"日 "+hourStr+":"+minuteStr;

                if (onEnsureListener!=null){
                    onEnsureListener.onEnsure(time, year,month,day);
                }
                cancel();
                break;
        }

    }
}
