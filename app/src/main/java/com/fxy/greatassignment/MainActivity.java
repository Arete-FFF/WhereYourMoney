package com.fxy.greatassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fxy.greatassignment.adapter.AccountAdapter;
import com.fxy.greatassignment.database.AccountBean;
import com.fxy.greatassignment.database.DBManager;
import com.fxy.greatassignment.utils.BudgetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    // 定义需要用到的控件
    // 定义今日收支
    ListView todayLv;
    // 定义button相关控件
    ImageView searchIv;
    Button editBtn;
    ImageButton moreBtn;

    // 定义数据源
    List<AccountBean> datas;
    // 定义每日数据的适配器
    AccountAdapter adapter;
    // 定义获取当前年月日
    int year , month, day;

    // 头布局相关控件
    View headerView;
    TextView topOutTv,topInTv,topbudgetTv,topConTv;


    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//展示页面
        // 初始化时间
        initTime();
        // 初始化绑定控件
        initView();

        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE);
        // 添加listview 头布局
        addLVHeaderView();
        // 初始化数据源
        datas = new ArrayList<>();
        // 设置适配器，加载到每一行数据中
        adapter = new AccountAdapter(this, datas);
        todayLv.setAdapter(adapter);
    }
    // 绑定控件
    private void initView() {
        todayLv = findViewById(R.id.main_lv);
        editBtn = findViewById(R.id.main_btn_edit);
        moreBtn = findViewById(R.id.main_btn_more);
        searchIv = findViewById(R.id.main_iv_search);
        // 绑定监听
        editBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
        searchIv.setOnClickListener(this);
    }

    private void addLVHeaderView() {
        //将布局转换成View对象
        headerView = getLayoutInflater().inflate(R.layout.item_mainlv_top, null);
        todayLv.addHeaderView(headerView);
        //查找头布局可用控件
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_money_out);
        topInTv = headerView.findViewById(R.id.item_mainlv_top_money_in);
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_budget);
        topConTv = headerView.findViewById(R.id.item_mainlv_top_day);

        topbudgetTv.setOnClickListener(this);
        headerView.setOnClickListener(this);
    }

    // 获取今日的具体时间
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    // 当activity获取焦点时，更新页面
    @Override
    protected void onResume() {
        super.onResume();
        // 加载数据库
        loadDBData();
        // 设置头布局的数据
        setTopTvShow();
    }

    private void setTopTvShow() {
        //获取今日支出和收入总金额，显示在view当中
        float incomeOneDay = DBManager.getSumMoneyOneDay(year, month, day, 1);
        float outcomeOneDay = DBManager.getSumMoneyOneDay(year, month, day, 0);
        String infoOneDay = "今日支出 ￥"+outcomeOneDay+"  收入 ￥"+incomeOneDay;
        topConTv.setText(infoOneDay);
        //获取本月收入和支出总金额，显示在view当中
        float incomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 1);
        float outcomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0);
        topInTv.setText("￥"+incomeOneMonth);
        topOutTv.setText("￥"+outcomeOneMonth);

        //设置显示预算剩余
        float bmoney = preferences.getFloat("bmoney", 0);//预算
        if (bmoney == 0) {
            topbudgetTv.setText("￥ 0");
        }else{
            float syMoney = bmoney-outcomeOneMonth;
            topbudgetTv.setText("￥"+syMoney);
        }
    }

    // 加载数据库数据
    private void loadDBData() {
        List<AccountBean> list = DBManager.getAccountListOneDayFromAccounttb(year, month, day);
        datas.clear();
        datas.addAll(list);
        adapter.notifyDataSetChanged();

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_iv_search:

                break;
            case R.id.main_btn_edit:
                Intent it1 = new Intent(this, WriteActivity.class);
                startActivity(it1);
                break;
            case R.id.main_btn_more:

                break;
            case R.id.item_mainlv_top_budget:
                showBudgetDialog();
                break;
        }
        if (view == headerView) {
            //头布局被点击了
//            Intent intent = new Intent();
//            intent.setClass(this, MonthChartActivity.class);
//            startActivity(intent);
        }

    }

    // 显示预算设置对话框
    private void showBudgetDialog() {
        BudgetDialog dialog = new BudgetDialog(this);
        // 展示对话框
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new BudgetDialog.OnEnsureListener() {
            @Override
            public void onEnsure(float money) {
                //将预算金额写入到共享参数当中，进行存储
                SharedPreferences.Editor editor = preferences.edit();
                editor.putFloat("bmoney",money);
                editor.commit();
                //计算剩余金额
                float outcomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0);
                float syMoney = money-outcomeOneMonth;//预算剩余 = 预算-支出
                topbudgetTv.setText("￥"+syMoney);
            }
        });
    }
}