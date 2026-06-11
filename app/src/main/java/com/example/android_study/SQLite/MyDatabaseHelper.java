package com.example.android_study.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper  extends SQLiteOpenHelper{
    public static final String CREATE_BOOK = "create table book ("
            + "id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text)";
    public Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        Log.d("MyDatabaseHelper", "Create database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //升级数据库
        db.execSQL("drop table if exists book");//删除数据库，去重新创建
        //因为如果已经创建了数据库，想要重新创建数据库，必须删除旧的数据库，只能通过这个方法或者卸载应用程序
        onCreate(db);
        Log.d("MyDatabaseHelper", "Upgrade database");
    }
}
