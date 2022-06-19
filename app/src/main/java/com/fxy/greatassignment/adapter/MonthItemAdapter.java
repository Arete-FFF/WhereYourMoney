package com.fxy.greatassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxy.greatassignment.R;
import com.fxy.greatassignment.database.MonthItemBean;

import java.math.BigDecimal;
import java.util.List;

/*
 * 账单详情listview适配器
 */
public class MonthItemAdapter extends BaseAdapter {
    Context context;
    List<MonthItemBean> data;
    LayoutInflater inflater;

    public MonthItemAdapter(Context context, List<MonthItemBean> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 初始化框架类
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_monthfrag_lv, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 获取显示内容
        MonthItemBean bean = data.get(position);
        // 分别对每个item设值
        holder.iv.setImageResource(bean.getsImageId());
        holder.typeTv.setText(bean.getType());

        // 获取ratio并将ratio转为string格式
        float ratio = bean.getRatio();
        BigDecimal temp = new BigDecimal(ratio * 100);
        String pert = temp.setScale(2, 4).floatValue() + "%";

        holder.ratioTv.setText(pert);
        holder.totalTv.setText("￥ " + bean.getTotalMoney());

        return convertView;
    }

    /*
     * 定义框架类，方便之后多次使用
     */
    class ViewHolder {
        TextView typeTv, ratioTv, totalTv;
        ImageView iv;

        public ViewHolder(View view) {
            typeTv = view.findViewById(R.id.item_monthfrag_tv_type);
            ratioTv = view.findViewById(R.id.item_monthfrag_tv_pert);
            totalTv = view.findViewById(R.id.item_monthfrag_tv_sum);
            iv = view.findViewById(R.id.item_monthfrag_iv);
        }
    }
}
