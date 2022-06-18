package com.fxy.greatassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxy.greatassignment.R;
import com.fxy.greatassignment.database.AccountBean;

import java.util.Calendar;
import java.util.List;

public class AccountAdapter extends BaseAdapter {
    //获取context和数据源
    Context context;
    List<AccountBean> datas;
    // 加载布局
    LayoutInflater inflater;
    // 获取时间用于显示
    int year,month,day;

    public AccountAdapter(Context context, List<AccountBean> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);//从context中获取，方便使用
        // 获取年月日
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        // 初始化holder
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_mainlv,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        // 传入数据库数据
        AccountBean bean = datas.get(position);
        holder.typeIv.setImageResource(bean.getsImageId());
        holder.name.setText(bean.getTypename());
        holder.remark.setText(bean.getBeizhu());
        holder.money.setText("￥ "+bean.getMoney());
        // 判断时间显示格式
        if (bean.getYear()==year&&bean.getMonth()==month&&bean.getDay()==day) {
            String time = bean.getTime().split(" ")[1];// 除去前后空格
            holder.time.setText("今天 "+time);
        }else {
            holder.time.setText(bean.getTime());
        }
        return convertView;
    }

    // 设置空间管理类，方便之后使用
    class  ViewHolder{
        ImageView typeIv;
        TextView name,remark,time,money;
        public ViewHolder(View view){
            typeIv = view.findViewById(R.id.item_mainlv_iv);
            name = view.findViewById(R.id.item_mainlv_tv_name);
            time = view.findViewById(R.id.item_mainlv_tv_time);
            remark = view.findViewById(R.id.item_mainlv_tv_remark);
            money = view.findViewById(R.id.item_mainlv_tv_money);
        }
    }
}
