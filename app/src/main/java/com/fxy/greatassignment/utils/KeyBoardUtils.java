package com.fxy.greatassignment.utils;
import com.fxy.greatassignment.R;


import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;


public class KeyBoardUtils {
    private final Keyboard k;    //自定义键盘
    private KeyboardView keyboardView;
    private EditText editText;

    public interface OnEnsureListener{
        public void onEnsure();
    }
    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public KeyBoardUtils(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_NULL);  //取消弹出系统键盘
        k = new Keyboard(this.editText.getContext(), R.xml.keyboard);

        this.keyboardView.setKeyboard(k);  //设置要显示键盘的样式
        this.keyboardView.setEnabled(true);  // 优先使用自定义键盘
        this.keyboardView.setPreviewEnabled(false); //预览自定义键盘
        this.keyboardView.setOnKeyboardActionListener(listener);  //设置键盘按钮被点击了的监听
    }

    KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {
        }
        @Override
        public void onRelease(int primaryCode) {
        }
        // 绑定所有key的响应
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable edittable = editText.getText();
            int start = editText.getSelectionStart();
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:   //删除情况，删除最右边的一个数字
                    if (edittable !=null && edittable.length()>0) {
                        if (start>0) {
                            edittable.delete(start-1,start);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_CANCEL:   //清零情况，直接清空edittabal
                    edittable.clear();
                    break;
                case Keyboard.KEYCODE_DONE:    //完成情况，获取这个edittable
                    onEnsureListener.onEnsure();   //通过接口回调的方法
                    break;
                default:  //其他数字直接插入
                    edittable.insert(start,Character.toString((char)primaryCode));
                    break;
            }
        }
        @Override
        public void onText(CharSequence text) {
        }
        @Override
        public void swipeLeft() {
        }
        @Override
        public void swipeRight() {
        }
        @Override
        public void swipeDown() {
        }
        @Override
        public void swipeUp() {
        }
    };

    //    显示键盘
    public void showKeyboard(){
        int visibility = keyboardView.getVisibility();
        if (visibility == View.INVISIBLE ||visibility==View.GONE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    //    隐藏键盘
    public void hideKeyboard(){
        int visibility = keyboardView.getVisibility();
        if (visibility== View.VISIBLE||visibility==View.INVISIBLE) {
            keyboardView.setVisibility(View.GONE);
        }
    }
}
