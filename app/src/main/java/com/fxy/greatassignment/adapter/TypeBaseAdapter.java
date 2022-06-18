package com.fxy.greatassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxy.greatassignment.R;
import com.fxy.greatassignment.database.TypeBean;

import java.util.List;
/*
 * 该适配器不考虑复用问题，因为所有item均存在于界面上
 */
public class TypeBaseAdapter extends BaseAdapter {
    Context context;
    // 定义beanlist 数据来源
    List<TypeBean> typeBeanList;
    // 选中位置
    public int selectPos = 0;
    public TypeBaseAdapter(Context context, List<TypeBean> typeBeanList) {
        this.context = context;
        this.typeBeanList = typeBeanList;
    }

    @Override
    public int getCount() {
        return typeBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return typeBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * adapter展示view函数
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_writefrag_gv,parent,false);
        //查找布局当中的控件
        ImageView iv = convertView.findViewById(R.id.item_writefrag_iv);
        TextView tv = convertView.findViewById(R.id.item_writefrag_tv);
        //获取指定位置的数据源
        TypeBean typeBean = typeBeanList.get(position);
        tv.setText(typeBean.getTypename());
        //判断当前位置是否为选中位置，如果是选中位置，就设置为带颜色的图片，否则为灰色图片
        if (selectPos == position) {
            iv.setImageResource(typeBean.getSimageId());
        }else{
            iv.setImageResource(typeBean.getImageId());
        }
        return convertView;
    }
}
