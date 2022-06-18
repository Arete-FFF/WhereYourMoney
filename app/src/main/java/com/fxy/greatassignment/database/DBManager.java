package com.fxy.greatassignment.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/*
 * 管理数据库的类
 * 负责增删改查
 */
public class DBManager {
    private static SQLiteDatabase db;
    /* 初始化数据库对象*/
    public static void initDB(Context context){
        DBOpenHelper helper = new DBOpenHelper(context);  //得到帮助类对象
        db = helper.getWritableDatabase();      //得到数据库对象
    }

    /**
     * 读取数据库当中的数据，写入内存集合里
     *   kind :表示收入或者支出
     * */
    public static List<TypeBean> getTypeList(int kind){
        List<TypeBean>list = new ArrayList<>();
        //读取typetb表当中的数据
        String sql = "select * from typetb where kind = "+kind;
        Cursor cursor = db.rawQuery(sql, null);
        //循环读取游标内容，存储到对象当中
        while (cursor.moveToNext()) {
            String type = cursor.getString(cursor.getColumnIndex("type"));
            int noCheckId = cursor.getInt(cursor.getColumnIndex("noCheckId"));
            int checkedId = cursor.getInt(cursor.getColumnIndex("checkedId"));
            int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            TypeBean typeBean = new TypeBean(id, type, noCheckId, checkedId, kind1);
            list.add(typeBean);
        }
        return list;
    }
}
