package com.fxy.greatassignment.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.fxy.greatassignment.R;

public class RemarkDialog extends Dialog implements View.OnClickListener {
    EditText editText;
    Button cancel, ensure;
    // 创建监听
    OnEnsureListener onEnsureListener;

    public RemarkDialog(@NonNull Context context) {
        super(context);
    }

    /*
     * 设定回调接口的方法
     */
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_remark_btn_cancel:
                cancel();
                break;
            case R.id.dialog_remark_btn_ensure:
                if (onEnsureListener != null) {
                    onEnsureListener.onEnsure();
                }
                break;
        }
    }

    /*
     * 获取数据的方法
     */
    public String getEditText() {
        return editText.getText().toString().trim();
    }

    /*
     * 设置弹出页面的大小
     */
    public void setDialogSize() {
        // 获取当前窗口的对象
        Window window = getWindow();
        // 获取窗口对象参数
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        //获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        //对话框窗口为屏幕窗口
        windowAttributes.width = (int) (d.getWidth());
        //显示在屏幕底部
        windowAttributes.gravity = Gravity.BOTTOM;
        //设置背景资源为透明
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(windowAttributes);

        //设置延迟弹出软键盘  否则会出现显示异常
        handler.sendEmptyMessageDelayed(1, 100);
    }
    /*
     * 弹出软键盘
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };


    public interface OnEnsureListener {
        public void onEnsure();
    }
}
