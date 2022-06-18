package com.fxy.greatassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.fxy.greatassignment.adapter.WriteFragmentPagerAdapter;
import com.fxy.greatassignment.frag_write.InFragment;
import com.fxy.greatassignment.frag_write.OutFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class WriteActivity extends AppCompatActivity {
    ViewPager viewPager;

    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        //查找控件
        viewPager = findViewById(R.id.write_vp);
        tabLayout = findViewById(R.id.write_tabs);
        //设置ViewPager加载页面
        initPager();
    }

    private void initPager() {
        //初始化ViewPager页面集合
        List<Fragment> fragmentList = new ArrayList<>();
        //创建收入和支出页面
        InFragment inFragment = new InFragment();//收入页面
        OutFragment outFragment = new OutFragment();//支出页面
        //添加到Fragment里面
        fragmentList.add(outFragment);
        fragmentList.add(inFragment);
        //创建适配器
        WriteFragmentPagerAdapter writeFragmentPagerAdapter = new WriteFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        //设置适配器
        viewPager.setAdapter(writeFragmentPagerAdapter);
        //关联TabLayout与ViewPaper
        tabLayout.setupWithViewPager(viewPager);
    }

    //点击事件
    public void ivonClick(View view){
        switch (view.getId()){
            case R.id.write_iv_back://若点击为❌图标
                finish();
                break;
        }
    }
}