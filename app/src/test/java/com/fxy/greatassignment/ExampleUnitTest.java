/*
 * 创建数据库
 */

public DBOpenHelper(@Nullable Context context) {
    super(context, "WhereYourMoney.db", null, 1);
}

/*
 * 创建数据库的方法，只有项目第一次运行时，会被调用
 */
@Override
public void onCreate(SQLiteDatabase db) {
    //创建表示类型的表
    String sql = "create table typetb(id integer primary key autoincrement,typename varchar(10),imageId integer,sImageId integer,kind integer)";
    db.execSQL(sql);
    insertType(db);
    //创建记账表
    sql = "create table accounttb(id integer primary key autoincrement,typename varchar(10),sImageId integer,beizhu varchar(80),money float," +
            "time varchar(60),year integer,month integer,day integer,kind integer)";
    db.execSQL(sql);
}

/
*
* 向数据表中插入一条信息
*/
public static void insertItemToAccounttb(AccountBean accountBean) {
    ContentValues values = new ContentValues();
    values.put("typename", accountBean.getTypename());
    values.put("sImageId", accountBean.getsImageId());
    values.put("beizhu", accountBean.getBeizhu());
    values.put("money", accountBean.getMoney());
    values.put("time", accountBean.getTime());
    values.put("year", accountBean.getYear());
    values.put("month", accountBean.getMonth());
    values.put("day", accountBean.getDay());
    values.put("kind", accountBean.getKind());
    db.insert("accounttb", null, values);
    Log.i("NoBug", "insert succeed!!!!!!!!!!!!");
}
