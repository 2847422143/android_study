package com.example.android_study.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyProvider extends ContentProvider {
    private static final String TAG = "MyProvider";
    private Context mContext;
    private DBHelper dbHelper = null;
    SQLiteDatabase db = null;


    public static final String AUTOHORITY = "com.human.lxczy";
    // 设置ContentProvider的唯一标识

    public static final int User_Code = 1;
    public static final int Job_Code = 2;

    // UriMatcher类使用:在ContentProvider 中注册URI
    private static final UriMatcher mMatcher;

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // 初始化
        mMatcher.addURI(AUTOHORITY, "user", User_Code);
        mMatcher.addURI(AUTOHORITY, "job", Job_Code);
        // 若URI资源路径 = content://com.human.lxczy/user ，则返回注册码User_Code
        // 若URI资源路径 = content://com.human.lxczy/job ，则返回注册码Job_Code
    }

    @Override
    public boolean onCreate() {
        mContext =getContext();
        // 在ContentProvider创建时对数据库进行初始化
        // 运行在主线程，故不能做耗时操作,此处仅作展示
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getWritableDatabase();

        // 初始化两个表的数据(先清空两个表,再各加入一个记录)
        db.execSQL("delete from user");
        db.execSQL("insert into user values(1,'lxc');");
        db.execSQL("insert into user values(2,'zy');");
        db.execSQL("insert into user values(3,'lsy');");

        db.execSQL("delete from job");
        db.execSQL("insert into job values(1,'Android');");
        db.execSQL("insert into job values(2,'teacher');");
        db.execSQL("insert into job values(3,'doctor');");

        return true;
    }
    /**
     * 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
     */
    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mMatcher.match(uri)) {
            case User_Code:
                tableName = DBHelper.USER_TABLE_NAME;
                break;
            case Job_Code:
                tableName = DBHelper.JOB_TABLE_NAME;
                break;
        }
        Log.d(TAG,"tableName = "+tableName);
        return tableName;
    }

//    projection：要查询的列名数组（ new String[]{"id", "name"} → 只查 user 表的 id 和 name 列）
//    selection ：查询条件（如 "id = ?" → 条件为 “id 等于某个值”）
//    selectionArgs：	填充 selection 中 ? 的参数数组和 selection 中的 ? 一一对应 （若 selection = "id = ?"，则 new String[]{"1"} → 最终条件 id = 1）
//    sortOrder：排序规则（如 "id DESC" → 按 id 降序排列"name ASC" → 按 name 升序排列）
// 查询 user 表中 id=2 的所有列，按 name 升序
//        Cursor cursor = query(
//            Uri.parse("content://com.human.lxczy/user"),
//            null,               // 查所有列
//            "id = ?",           // 条件：id等于?
//            new String[]{"2"},  // 填充?为2
//            "name ASC"          // 按name升序
//        );
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
        // 该方法在最下面
        String table = getTableName(uri);

//        // 通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);

        // 查询数据
        return db.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    //（返回数据 MIME 类型）
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    //values：要插入的键值对（key = 列名，value = 列值）null 表示插入空行（无意义，通常传非空）
    //（ContentValues values = new ContentValues();
    //values.put("id", 4);
    //values.put("name", "wxy");
    //→ 向 user 表插入 id=4、name=wxy 的行）
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
        // 该方法在最下面
        String table = getTableName(uri);

        // 向该表添加数据
        db.insert(table, null, values);

        // 当该URI的ContentProvider数据发生变化时，通知外界（即访问该ContentProvider数据的访问者）
        mContext.getContentResolver().notifyChange(uri, null);

//        // 通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);

        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = getTableName(uri);
        int deleteCount = db.delete(table, selection, selectionArgs);
        return deleteCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = getTableName(uri);
        // 更新数据
        int updateCount = db.update(table, values, selection, selectionArgs);
        return updateCount;
    }
}
