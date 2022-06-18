package com.fxy.greatassignment.frag_month;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fxy.greatassignment.R;
import com.fxy.greatassignment.adapter.MonthItemAdapter;
import com.fxy.greatassignment.database.DBManager;
import com.fxy.greatassignment.database.MonthItemBean;

import java.util.ArrayList;
import java.util.List;

/*
 * 添加支出的listview
 */
public class OutMonthFragment extends Fragment {
    // 定义需要绑定的控件
    ListView monthLv;
    // 定义时间,用于获取数据
    int year;
    int month;
    // 定义数据源
    List<MonthItemBean> data;
    private MonthItemAdapter itemAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_in_month, container, false);
        monthLv = view.findViewById(R.id.frag_month_lv);
        // 获取Activity传递的数据
        Bundle bundle = getArguments();
        year = bundle.getInt("year");
        month = bundle.getInt("month");
        // 舒适化设置数据源
        data = new ArrayList<>();
        //设置适配器
        itemAdapter = new MonthItemAdapter(getContext(), data);
        monthLv.setAdapter(itemAdapter);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        loadData(year,month,0);
    }


    public void loadData(int year,int month,int kind) {
        List<MonthItemBean> list = DBManager.getMonthListFromAccounttb(year, month, kind);
        data.clear();
        data.addAll(list);
        itemAdapter.notifyDataSetChanged();
    }
}