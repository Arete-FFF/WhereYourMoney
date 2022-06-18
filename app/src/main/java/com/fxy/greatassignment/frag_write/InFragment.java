package com.fxy.greatassignment.frag_write;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fxy.greatassignment.R;
import com.fxy.greatassignment.database.DBManager;
import com.fxy.greatassignment.database.TypeBean;

import java.util.List;

/**
 * 添加新账单的fragment
 * 收入账单
 */
public class InFragment extends FatherFragment {
    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据库当中的数据源
        //获取数据库数据
        List<TypeBean> outlist = DBManager.getTypeList(1);
        typeBeanList.addAll(outlist);
        //使用adapter输出
        adapter.notifyDataSetChanged();
        type.setText("其他");
        imageView.setImageResource(R.mipmap.in_qita_ls);
    }

}