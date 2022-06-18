package com.fxy.greatassignment.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.fxy.greatassignment.R;

public class RemarkDialog extends Dialog implements View.OnClickListener {
    EditText editText;
    Button cancel, ensure;
    OnEnsureListener onEnsureListener;

    // 设定回调接口的方法
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置对话框显示布局
        setContentView(R.layout.dialog_remark);
        // 绑定xml id
        editText = findViewById(R.id.dialog_remark_et);
        cancel = findViewById(R.id.dialog_remark_btn_cancel);
        ensure = findViewById(R.id.dialog_remark_btn_ensure);
        // 绑定监听对象
        cancel.setOnClickListener(this);
        ensure.setOnClickListener(this);
    }

    public RemarkDialog(@NonNull Context context) {
        super(context);
    }

    public interface OnEnsureListener{
        public void onEnsure();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_remark_btn_cancel:
                cancel();
                break;
            case R.id.dialog_remark_btn_ensure:
                if (onEnsureListener!=null) {
                    onEnsureListener.onEnsure();
                }
                break;
        }
    }

    // 获取数据的方法
    public String getEditText(){
        return editText.getText().toString().trim();
    }
}
