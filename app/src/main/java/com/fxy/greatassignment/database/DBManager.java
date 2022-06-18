package com.fxy.greatassignment.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.math.BigDecimal;
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

    /*
     * 读取数据库当中的数据，写入内存集合里
     *   kind :表示收入或者支出
     */
    public static List<TypeBean>getTypeList(int kind){
        List<TypeBean>list = new ArrayList<>();
        //读取typetb表当中的数据
        String sql = "select * from typetb where kind = "+kind;
        Cursor cursor = db.rawQuery(sql, null);
        //循环读取游标内容，存储到对象当中
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range") int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            TypeBean typeBean = new TypeBean(id, typename, imageId, sImageId, kind1);
            list.add(typeBean);
        }
        return list;
    }

    // 向数据表中插入一条信息
    public static void insertItemToAccounttb(AccountBean accountBean) {
        ContentValues values = new ContentValues();
        values.put("typename",accountBean.getTypename());
        values.put("sImageId",accountBean.getsImageId());
        values.put("beizhu",accountBean.getBeizhu());
        values.put("money",accountBean.getMoney());
        values.put("time",accountBean.getTime());
        values.put("year",accountBean.getYear());
        values.put("month",accountBean.getMonth());
        values.put("day",accountBean.getDay());
        values.put("kind",accountBean.getKind());
        db.insert("accounttb",null,values);
        Log.i("NoBug", "insert succeed!!!!!!!!!!!!");
    }

    /*
     * 获取记账表当中某一天的所有支出或者收入情况
     * */
    public static List<AccountBean>getAccountListOneDayFromAccounttb(int year,int month,int day){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? and day=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + ""});
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }
    /*
     * 获取记账表当中某一月的所有支出或者收入情况
     */
    public static List<AccountBean>getAccountListOneMonthFromAccounttb(int year,int month){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + ""});
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("money"));
            @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex( "day"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }
    /*
     * 获取某一天的支出或者收入的总金额   kind：支出==0    收入===1
     */
    public static float getSumMoneyOneDay(int year,int month,int day,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and day=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }
    /*
     * 获取某一月的支出或者收入的总金额   kind：支出==0    收入===1
     */
    public static float getSumMoneyOneMonth(int year,int month,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }
    /*
     * 统计某月份支出或者收入情况有多少条  收入-1   支出-0
     */
    public static int getCountItemOneMonth(int year,int month,int kind){
        int total = 0;
        String sql = "select count(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int count = cursor.getInt(cursor.getColumnIndex("count(money)"));
            total = count;
        }
        return total;
    }
    /*
     * 获取某一年的支出或者收入的总金额   kind：支出==0    收入===1
     */
    public static float getSumMoneyOneYear(int year,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where year=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }

    public static int deleteItemFromAccounttbById(int click_id) {
        int i = db.delete("accounttb", "id=?", new String[]{click_id + ""});
        return i;
    }
    // 根据备注查找记录
    public static List<AccountBean> getAccountListByRemarkFromAccounttb(String text) {
        List<AccountBean>list = new ArrayList<>();
        //使用模糊查询
        String sql = "select * from accounttb where beizhu like '%"+text+"%'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("money"));
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
            @SuppressLint("Range") int month = cursor.getInt(cursor.getColumnIndex("month"));
            @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }

    // 删除所有数据
    public static void deleteAllAccount() {
        String sql = "delete from accounttb";
        db.execSQL(sql);
    }
    // 查询数据库所有数据
    public static List<AccountBean> getAllAccountListFromAccounttb() {
        List<AccountBean>list = new ArrayList<>();
        // 查询所有数据
        String sql = "select * from accounttb order by year, month, day desc";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("money"));
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
            @SuppressLint("Range") int month = cursor.getInt(cursor.getColumnIndex("month"));
            @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }

    // 获取所有数据的总金额
    public static float getSumMoney(int kind) {
        float total = 0.0f;
        String sql = "select sum(money) from accounttb where  kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }
}
