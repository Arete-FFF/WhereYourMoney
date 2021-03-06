package com.fxy.greatassignment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fxy.greatassignment.database.DBManager;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void onClick(View view) {
        //对所有控件进行选择
        switch (view.getId()) {
            case R.id.setting_iv_back:
                finish();
                break;
            case R.id.setting_tv_clear:
                showDeleteDialog();
                break;
            case R.id.setting_tv_all:
                //跳转页面
                Intent it1 = new Intent(this, HistoryActivity.class);
                startActivity(it1);
                break;

        }
    }

    /*
     * 显示删除提示，监听选项并反馈
     */
    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除提示")
                .setMessage("您确定要删除所有记录么？\n注意：删除后无法恢复，请慎重选择！")
                .setPositiveButton("取消", null)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //调用DBManager的删除所有数据
                        DBManager.deleteAllAccount();
                        // 使用toast显示结果
                        Toast.makeText(SettingActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                    }
                });
        // 显示提示
        builder.create().show();
    }
}