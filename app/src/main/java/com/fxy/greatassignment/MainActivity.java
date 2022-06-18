package com.fxy.greatassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    // 定义非实时更新组件
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//展示页面
        // 初始化时间
        initTime();
        // 初始化绑定控件
        initView();
        // 设置长按方法
        setLVLongClickListener();
        // 设置非实时更新
        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE);
        // 添加listview 头布局
        addLVHeaderView();
        // 初始化数据源
        datas = new ArrayList<>();
        // 设置适配器，加载到每一行数据中
        adapter = new AccountAdapter(this, datas);
        todayLv.setAdapter(adapter);
    }
    /*
     * 初始化绑定控件，并且绑定监听
     */
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

    /*
     * 设置listview长按事件
     */
    private void setLVLongClickListener() {
        todayLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //点击了头布局
                if (position == 0) {
                    return false;
                }
                int pos = position-1;
                //获取正在被点击的这条信息
                AccountBean clickBean = datas.get(pos);
                //弹出提示用户是否删除的对话框
                showDeleteItemDialog(clickBean);
                return false;
            }
        });
    }
    /*
     * 弹出是否删除对话框，并监听给出反馈
     */
    private void showDeleteItemDialog(AccountBean clickBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录么？")
                .setNegativeButton("取消",null)//取消则正常返回
                .setPositiveButton("确定", new DialogInterface.OnClickListener() { //确定则调用数据库删除
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int click_id = clickBean.getId();
                        //执行删除的操作
                        DBManager.deleteItemFromAccounttbById(click_id);
                        //实时刷新，移除集合当中的对象
                        datas.remove(clickBean);
                        //适配器更新数据
                        adapter.notifyDataSetChanged();
                        //改变头布局TextView显示的内容
                        setTopTvShow();
                    }
                });
        //显示对话框
        builder.create().show();
    }

    /*
     * 添加标题view
     */
    private void addLVHeaderView() {
        //将布局转换成View对象
        headerView = getLayoutInflater().inflate(R.layout.item_mainlv_top, null);
        todayLv.addHeaderView(headerView);
        //查找头布局可用控件
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_money_out);
        topInTv = headerView.findViewById(R.id.item_mainlv_top_money_in);
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_budget);
        topConTv = headerView.findViewById(R.id.item_mainlv_top_day);
        //绑定监听
        topbudgetTv.setOnClickListener(this);
        headerView.setOnClickListener(this);
    }

    /*
     * 获取今日的具体时间
     */
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    /*
     * 当activity获取焦点时，更新页面
     */
    @Override
    protected void onResume() {
        super.onResume();
        // 加载数据库
        loadDBData();
        // 设置头布局的数据
        setTopTvShow();
    }

    /*
     * 设置顶部本月状态栏
     */
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
        float bmoney = preferences.getFloat("bmoney", 0);
        if (bmoney == 0) {
            topbudgetTv.setText("￥ 0");
        }else{
            float syMoney = bmoney-outcomeOneMonth;
            topbudgetTv.setText("￥"+syMoney);
        }
    }

    /*
     * 加载数据库数据
     */
    private void loadDBData() {
        // 获取当天的预算
        List<AccountBean> list = DBManager.getAccountListOneDayFromAccounttb(year, month, day);
        datas.clear();
        datas.addAll(list);
        adapter.notifyDataSetChanged();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            // 点击搜索图标
            case R.id.main_iv_search:
                //跳转页面
                Intent it1 = new Intent(this, SearchActivity.class);
                startActivity(it1);
                break;
            // 点击"记"图标
            case R.id.main_btn_edit:
                Intent it2 = new Intent(this, WriteActivity.class);
                startActivity(it2);
                break;
            // 点击"more"图标
            case R.id.main_btn_more:
                Intent it3 = new Intent(this, SettingActivity.class);
                startActivity(it3);
                break;
            // 点击预算数字
            case R.id.item_mainlv_top_budget:
                showBudgetDialog();
                break;
        }
        // 点击head view
        if (view == headerView) {
            Intent intent = new Intent();
            intent.setClass(this, MonthActivity.class);
            startActivity(intent);
        }

    }

    /*
     * 显示预算设置对话框
     */
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