package com.fxy.greatassignment.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fxy.greatassignment.R;

public class BudgetDialog extends Dialog implements View.OnClickListener {
    // 定义绑定控件
    ImageView cancelIv;
    Button ensureBtn;
    EditText moneyEt;
    // 定义接口
    OnEnsureListener onEnsureListener;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            //自动弹出软键盘的方法
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };

    public BudgetDialog(@NonNull Context context) {
        super(context);
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定页面的控件
        setContentView(R.layout.dialog_budget);
        cancelIv = findViewById(R.id.dialog_budget_iv_error);
        ensureBtn = findViewById(R.id.dialog_budget_btn_ensure);
        moneyEt = findViewById(R.id.dialog_budget_et);

        // 监听button
        cancelIv.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_budget_iv_error:
                //取消对话框
                cancel();
                break;
            case R.id.dialog_budget_btn_ensure:
                //获取输入数据数值
                String data = moneyEt.getText().toString();
                if (TextUtils.isEmpty(data)) {
                    Toast.makeText(getContext(), "输入数据不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                float money = Float.parseFloat(data);
                if (money <= 0) {
                    Toast.makeText(getContext(), "预算金额必须大于0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (onEnsureListener != null) {
                    onEnsureListener.onEnsure(money);
                }
                cancel();
                break;
        }
    }

    /*
     * 设置Dialog的尺寸和屏幕尺寸一致
     */
    public void setDialogSize() {
        //获取当前窗口对象
        Window window = getWindow();
        // 获取窗口对象的参数
        WindowManager.LayoutParams wlp = window.getAttributes();
        // 获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        //对话框窗口为屏幕窗口
        wlp.width = (int) (d.getWidth());
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
        // 等待100ms 再自动弹出软键盘
        handler.sendEmptyMessageDelayed(1, 100);
    }

    /*
     * 确定接口方法
     */
    public interface OnEnsureListener {
        public void onEnsure(float money);
    }
}
