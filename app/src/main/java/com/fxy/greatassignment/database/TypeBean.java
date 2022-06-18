package com.fxy.greatassignment.database;
/*
 * 表示类型的类
 */

public class TypeBean {
    int id;
    String type;   //类型名称
    int noCheckID; //未被选中图片的ID
    int checkedID; //选中图片的ID
    int kind;      //支出:0   收入:1

    public TypeBean() {
    }

    public TypeBean(int id, String type, int noCheckID, int checkedID, int kind) {
        this.id = id;
        this.type = type;
        this.noCheckID = noCheckID;
        this.checkedID = checkedID;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNoCheckID() {
        return noCheckID;
    }

    public void setNoCheckID(int noCheckID) {
        this.noCheckID = noCheckID;
    }

    public int getCheckedID() {
        return checkedID;
    }

    public void setCheckedID(int checkedID) {
        this.checkedID = checkedID;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
