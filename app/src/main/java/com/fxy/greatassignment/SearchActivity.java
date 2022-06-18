package com.fxy.greatassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxy.greatassignment.adapter.AccountAdapter;
import com.fxy.greatassignment.database.AccountBean;
import com.fxy.greatassignment.database.DBManager;


import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    // 定义需要用到的控件
    ListView searchLv;
    EditText searchEt;
    TextView emptyTv;
    // 定义数据源
    List<AccountBean>mDatas;
    // 定义适配器
    AccountAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // 初始化控件
        initView();
        // 初始化数据
        mDatas = new ArrayList<>();
        // 初始化控件
        adapter = new AccountAdapter(this,mDatas);
        searchLv.setAdapter(adapter);
        // 当无数据时，显示的控件
        searchLv.setEmptyView(emptyTv);
    }

    private void initView() {
        // 绑定各个控件
        searchEt = findViewById(R.id.search_et);
        searchLv = findViewById(R.id.search_lv);
        emptyTv = findViewById(R.id.search_tv_empty);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            // 点击❌时直接返回
            case R.id.search_iv_back:
                finish();
                break;
            // 点击搜索图标时的搜索操作
            case R.id.search_iv_sh:
                String msg = searchEt.getText().toString().trim();
                // 判断输入内容是否为空，如果为空，就提示不能搜索
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(this,"输入内容不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                // 开始搜索
                List<AccountBean> list = DBManager.getAccountListByRemarkFromAccounttb(msg);
                mDatas.clear();
                // 添加数据源
                mDatas.addAll(list);
                // 数据更新
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
