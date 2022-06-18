package com.fxy.greatassignment;

import android.app.Application;

import com.fxy.greatassignment.database.DBManager;

/*
 * 表示全局应用
 */
public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化数据库
        DBManager.initDB(getApplicationContext());
    }
}

