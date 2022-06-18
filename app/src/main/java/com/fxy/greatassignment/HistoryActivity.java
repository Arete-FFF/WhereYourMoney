package com.fxy.greatassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fxy.greatassignment.adapter.AccountAdapter;
import com.fxy.greatassignment.database.AccountBean;
import com.fxy.greatassignment.database.DBManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    // 定义需要绑定的框架
    ListView historyLv;
    TextView moneyTv;
    // 定义数据来源
    List<AccountBean> mDatas;
    // 定义adapter
    AccountAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        // 初始化框架和数据
        historyLv = findViewById(R.id.history_lv);
        moneyTv = findViewById(R.id.history_tv_money);
        mDatas = new ArrayList<>();
        // 设置适配器
        adapter = new AccountAdapter(this,mDatas);
        historyLv.setAdapter(adapter);

        // 获取所有数据和总收入支出
        loadData();
        setLVClickListener();
    }
    /*
     * 设置ListView每一个item的长按事件
     */
    private void setLVClickListener() {
        historyLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AccountBean accountBean = mDatas.get(position);
                deleteItem(accountBean);
                return false;
            }
        });
    }
    /*
     * 监听是否确认删除，并给出反馈
     */
    private void deleteItem(final AccountBean accountBean) {
        final int delId = accountBean.getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录么？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager.deleteItemFromAccounttbById(delId);
                        mDatas.remove(accountBean);   //实时刷新，从数据源删除
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.create().show();
    }

    /*
     * 获取所有数据
     */
    private void loadData() {
        // 获取金额
        float income = DBManager.getSumMoney(1);
        float outcome = DBManager.getSumMoney(0);
        String info = "累计支出 ￥"+outcome+"  累计收入 ￥"+income;
        moneyTv.setText(info);
        // 获取数据
        List<AccountBean> list = DBManager.getAllAccountListFromAccounttb();
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.history_iv_back:
                finish();
                break;
        }
    }
}