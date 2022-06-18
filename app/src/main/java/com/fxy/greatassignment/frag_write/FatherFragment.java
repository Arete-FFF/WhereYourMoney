package com.fxy.greatassignment.frag_write;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxy.greatassignment.R;
import com.fxy.greatassignment.database.AccountBean;
import com.fxy.greatassignment.database.DBManager;
import com.fxy.greatassignment.database.TypeBean;
import com.fxy.greatassignment.utils.KeyBoardUtils;
import com.fxy.greatassignment.utils.RemarkDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class FatherFragment extends Fragment implements View.OnClickListener {
    // 初始化xml中的view
    KeyboardView keyboardView;
    EditText editText;
    ImageView imageView;
    TextView type,remark,time;
    GridView gridView;
    // 创建Bean List
    List<TypeBean> typeBeanList;
    TypeBaseAdapter adapter;
    // 创建记账数据对象
    AccountBean accountBean;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean = new AccountBean();   //创建对象
        accountBean.setTypename("其他");
        accountBean.setsImageId(R.mipmap.ic_qita_hs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_out,container,false);
        //初始化view
        initView(view);
        //获取当前时间显示到右下角
        setInitTime();
        // 给GridView填充数据
        loadDataToGV();
        //设置GridView每一项的点击事件
        setGVListener();
        return view;
    }

    private void setInitTime() {
        Date date = new Date();
        // 设置时间的格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        // 获取当前时间
        String time_now = sdf.format(date);
        time.setText(time_now);
        accountBean.setTime(time_now);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(month);
        accountBean.setDay(day);
    }

    private void setGVListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectPos = position;
                adapter.notifyDataSetInvalidated();  //提示绘制发生变化了

                // 绘制绑定文本
                TypeBean typeBean = typeBeanList.get(position);
                String typename = typeBean.getTypename();
                type.setText(typename);
                accountBean.setTypename(typename);

                // 绘制绑定图片
                int simageId = typeBean.getSimageId();
                imageView.setImageResource(simageId);
                accountBean.setsImageId(simageId);
            }
        });
    }


    // 给GridView填充数据
    public void loadDataToGV() {
        typeBeanList = new ArrayList<>();//创建beanlist
        adapter = new TypeBaseAdapter(getContext(), typeBeanList);//创建新adapter
        gridView.setAdapter(adapter);

    }

    private void initView(View view) {
        keyboardView = view.findViewById((R.id.frag_write_keyboard));
        editText = view.findViewById(R.id.frag_write_et);
        imageView = view.findViewById(R.id.frag_write_iv);
        gridView = view.findViewById(R.id.frag_write_gv);
        type = view.findViewById(R.id.frag_write_tv);
        remark = view.findViewById(R.id.frag_write_tv_remark);
        time = view.findViewById(R.id.frag_write_tv_time);

        // 绑定点击事件
        remark.setOnClickListener(this);
        time.setOnClickListener(this);

        //显示自定义软键盘
        KeyBoardUtils boardUtils = new KeyBoardUtils(keyboardView, editText);
        boardUtils.showKeyboard();

        //监听按钮
        boardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                // TODO 点击确定按钮
                String moneyStr = editText.getText().toString();
                if (TextUtils.isEmpty(moneyStr)||moneyStr.equals("0")) {
                    getActivity().finish();
                    return;
                }
                float money = Float.parseFloat(moneyStr);
                accountBean.setMoney(money);
                // TODO 记录当前edittext信息，保存到数据库中
                saveAccountToDB();
                // TODO 返回上级页面
                getActivity().finish();
            }
        });
    }

    // 子类重写该方法，支出与收入不同
    public void saveAccountToDB() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_write_tv_time:
                // 展示选择日期对话框
                showTimeDialog();
                break;
            case R.id.frag_write_tv_remark:
                // 展示备注对话框
                showBZDialog();
                break;
        }
    }

    // 弹出备注对话框
    public void showBZDialog() {
        final RemarkDialog remarkDialog = new RemarkDialog(getContext());
        remarkDialog.show();
//        remarkDialog.setRemarkSize();
        remarkDialog.setOnEnsureListener(new RemarkDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String text = remarkDialog.getEditText();
                if (!TextUtils.isEmpty(text)) {
                    remark.setText(text);
                    accountBean.setBeizhu(text);
                }
                remarkDialog.cancel();
            }
        });
    }

    private void showTimeDialog() {
    }
}