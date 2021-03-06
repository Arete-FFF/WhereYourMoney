package com.fxy.greatassignment.frag_write;

import com.fxy.greatassignment.R;
import com.fxy.greatassignment.database.DBManager;
import com.fxy.greatassignment.database.TypeBean;

import java.util.List;

/*
 * 添加新账单的fragment
 * 收入账单
 */
public class InFragment extends FatherFragment {
    /*
     * 重载数据加载方法
     */
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

    /*
     * 重载写入数据库函数
     */
    @Override
    public void saveAccountToDB() {
        accountBean.setKind(1);
        DBManager.insertItemToAccounttb(accountBean);
    }
}