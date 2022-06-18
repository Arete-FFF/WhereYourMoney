package com.fxy.greatassignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.fxy.greatassignment.R;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context) {
        super(context, "YourMoney.db", null, 1);
    }

    // 创建数据库，项目第一次运行会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表的类型
        String sql = "create table typetb(id integer primary key autoincrement,type varchar(10),noCheckID integer,checkedID integer,kind integer)";
        //执行sql语言 创建数据库
        db.execSQL(sql);
        //向数据库插入数据
        insertType(db);
    }

    private void insertType(SQLiteDatabase db) {
        //向类型表中插入元素
        String sql = "insert into typetb (type,noCheckId,checkedId,kind) values (?,?,?,?)";

        db.execSQL(sql,new Object[]{"其他", R.mipmap.ic_qita,R.mipmap.ic_qita_hs,0});
        db.execSQL(sql,new Object[]{"餐饮", R.mipmap.ic_canyin,R.mipmap.ic_canyin_hs,0});
        db.execSQL(sql,new Object[]{"零食", R.mipmap.ic_lingshi,R.mipmap.ic_lingshi_hs,0});
        db.execSQL(sql,new Object[]{"交通", R.mipmap.ic_jiaotong,R.mipmap.ic_jiaotong_hs,0});
        db.execSQL(sql,new Object[]{"购物", R.mipmap.ic_gouwu,R.mipmap.ic_gowu_hs,0});
        db.execSQL(sql,new Object[]{"教育", R.mipmap.ic_jiaoyu,R.mipmap.ic_jiaoyu_hs,0});
        db.execSQL(sql,new Object[]{"日用", R.mipmap.ic_riyong,R.mipmap.ic_riyong_hs,0});
        db.execSQL(sql,new Object[]{"娱乐", R.mipmap.ic_yule,R.mipmap.ic_yule_hs,0});
        db.execSQL(sql,new Object[]{"服饰", R.mipmap.ic_fushi,R.mipmap.ic_fushi_hs,0});
        db.execSQL(sql,new Object[]{"通讯", R.mipmap.ic_tongxun,R.mipmap.ic_tongxun_hs,0});
        db.execSQL(sql,new Object[]{"住宅", R.mipmap.ic_zhuzhai,R.mipmap.ic_zhuzhai_hs,0});
        db.execSQL(sql,new Object[]{"烟酒", R.mipmap.ic_yanjiu,R.mipmap.ic_yanjiu_hs,0});
        db.execSQL(sql,new Object[]{"医疗", R.mipmap.ic_yiliao,R.mipmap.ic_yiliao_hs,0});
        db.execSQL(sql,new Object[]{"水电", R.mipmap.ic_shuidian,R.mipmap.ic_shuidian_hs,0});


        db.execSQL(sql,new Object[]{"其他", R.mipmap.in_qita,R.mipmap.in_qita_ls,1});
        db.execSQL(sql,new Object[]{"工资", R.mipmap.in_gongzi,R.mipmap.in_gongzi_ls,1});
        db.execSQL(sql,new Object[]{"奖金", R.mipmap.in_jiangjin,R.mipmap.in_jiangjin_ls,1});
        db.execSQL(sql,new Object[]{"交易", R.mipmap.in_jiaoyi,R.mipmap.in_jiaoyi_ls,1});
        db.execSQL(sql,new Object[]{"借入", R.mipmap.in_jieru,R.mipmap.in_jieru_ls,1});
        db.execSQL(sql,new Object[]{"投资", R.mipmap.ic_touzi,R.mipmap.in_touzi_ls,1});
        db.execSQL(sql,new Object[]{"意外所得", R.mipmap.in_yiwai,R.mipmap.in_yiwai_ls,1});
    }

    // 更新数据库，数据库更新版本调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
