package com.fxy.greatassignment.frag_write;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxy.greatassignment.R;
import com.fxy.greatassignment.database.DBManager;
import com.fxy.greatassignment.database.TypeBean;
import com.fxy.greatassignment.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加新账单的fragment
 * 支出账单
 */
public class OutFragment extends Fragment {
    // 初始化xml中的view
    KeyboardView keyboardView;
    EditText editText;
    ImageView imageView;
    TextView type,remark,time;
    GridView gridView;
    List<TypeBean> typeBeanList;
    TypeBaseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_out,container,false);
        //初始化view
        initView(view);
        // 给GridView填充数据
        loadDataToGV();
        return view;
    }


    // 给GridView填充数据
    private void loadDataToGV() {
        typeBeanList = new ArrayList<>();//创建beanlist
        adapter = new TypeBaseAdapter(getContext(), typeBeanList);//创建新adapter
        gridView.setAdapter(adapter);
        //获取数据库数据
        List<TypeBean> outlist = DBManager.getTypeList(0);
        typeBeanList.addAll(outlist);
        //使用adapter输出
        adapter.notifyDataSetChanged();
    }

    private void initView(View view) {
        keyboardView = view.findViewById((R.id.frag_write_keyboard));
        editText = view.findViewById(R.id.frag_write_et);
        imageView = view.findViewById(R.id.frag_write_iv);
        gridView = view.findViewById(R.id.frag_write_gv);
        type = view.findViewById(R.id.frag_write_tv);
        remark = view.findViewById(R.id.frag_write_tv_remark);
        time = view.findViewById(R.id.frag_write_tv_time);

        //显示自定义软键盘
        KeyBoardUtils boardUtils = new KeyBoardUtils(keyboardView, editText);
        boardUtils.showKeyboard();

        //监听按钮
        boardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                // TODO 点击确定按钮
                // TODO 记录当前edittext信息，保存到数据库中
                // TODO 返回上级页面
            }
        });
    }
}