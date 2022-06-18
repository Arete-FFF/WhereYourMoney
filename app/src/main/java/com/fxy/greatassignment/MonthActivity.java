package com.fxy.greatassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fxy.greatassignment.adapter.MonthVPAdapter;
import com.fxy.greatassignment.database.DBManager;
import com.fxy.greatassignment.frag_month.InMonthFragment;
import com.fxy.greatassignment.frag_month.OutMonthFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthActivity extends AppCompatActivity {
    // 定义需要绑定的控件
    Button inBtn,outBtn;
    TextView inTv,outTv;
    ViewPager viewPager;

    // 获取当前年月
    int year;
    int month;

    // 定义frag列表
    List<Fragment> fragmentList;
    // 定义frag对象
    private InMonthFragment inMonthFragment;
    private OutMonthFragment outMonthFragment;
    // 定义适配器对象
    private MonthVPAdapter monthVPAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);
        // 初始化控件
        initView();
        // 初始化时间
        initTime();
        // 初始化数据并展示
        initDate(year, month);
        // 初始化fragment 填充viewpaper
        initFrag();
    }

    /*
     * 用frag填充viewpaper
     */
    private void initFrag() {
        // 初始化list
        fragmentList = new ArrayList<>();
        //添加Fragment的对象
        inMonthFragment = new InMonthFragment();
        outMonthFragment = new OutMonthFragment();
        //添加数据到Fragment当中
        Bundle bundle = new Bundle();
        bundle.putInt("year",year);
        bundle.putInt("month",month);
        inMonthFragment.setArguments(bundle);
        outMonthFragment.setArguments(bundle);
        // 将Fragment添加到数据源当中
        fragmentList.add(outMonthFragment);
        fragmentList.add(inMonthFragment);
        // 使用适配器
        monthVPAdapter = new MonthVPAdapter(getSupportFragmentManager(), fragmentList);
        // 将Fragment加载到Acitivy当中
        viewPager.setAdapter(monthVPAdapter);
        // 绑定按钮与listView
        setVPSelectListener();
    }
    /*
     * 绑定按钮与listView,使其同步变化
     */
    private void setVPSelectListener() {
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                setButtonStyle(position);
            }
        });
    }

    /*
     * 初始化时间， 为了获取当前月数据
     */
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
    }

    /*
     * 传入时间，记录当前月数据
     */
    private void initDate(int year, int month) {
        float inMoneyOneMonth = DBManager.getSumMoneyOneMonth(year, month, 1);  //收入总钱数
        float outMoneyOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0); //支出总钱数
        int incountItemOneMonth = DBManager.getCountItemOneMonth(year, month, 1);  //收入多少笔
        int outcountItemOneMonth = DBManager.getCountItemOneMonth(year, month, 0); //支出多少笔
        inTv.setText("共"+incountItemOneMonth+"笔收入, ￥ "+inMoneyOneMonth);
        outTv.setText("共"+outcountItemOneMonth+"笔支出, ￥ "+outMoneyOneMonth);
    }

    /*
     * 初始化绑定控件
     */
    private void initView() {
        // 绑定需要用到的控件
        inBtn = findViewById(R.id.month_btn_in);
        outBtn = findViewById(R.id.month_btn_out);
        inTv = findViewById(R.id.month_tv_in);
        outTv = findViewById(R.id.month_tv_out);
        viewPager = findViewById(R.id.month_vp);
    }

    /*
     * 设置点击事件
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.month_iv_back:
                finish();
                break;
            case R.id.month_btn_in:
                setButtonStyle(1);
                viewPager.setCurrentItem(1);
                break;
            case R.id.month_btn_out:
                setButtonStyle(0);
                viewPager.setCurrentItem(0);
                break;
        }
    }

    /*
     * 设置按钮样式的改变  支出 0  收入 1
     */
    private void setButtonStyle(int kind){
        if (kind == 0) {
            outBtn.setBackgroundResource(R.drawable.dialog_btn_bg_2);
            outBtn.setTextColor(Color.WHITE);
            inBtn.setBackgroundResource(R.drawable.dialog_btn_bg_1);
            inBtn.setTextColor(Color.BLACK);
        }else if (kind == 1){
            inBtn.setBackgroundResource(R.drawable.dialog_btn_bg_2);
            inBtn.setTextColor(Color.WHITE);
            outBtn.setBackgroundResource(R.drawable.dialog_btn_bg_1);
            outBtn.setTextColor(Color.BLACK);
        }
    }
}