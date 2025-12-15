package com.example.android_study.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class sharedPreferencesImpl {
    private static Map<String,String> map = new HashMap<>();

    public static Map<String,String> getMap(){
        return map;
    }

    // MODE_PRIVATE:私有模式，仅当前应用访问
    // MODE_WORLD_READABLE:允许其他应用读取该文件
    // MODE_WORLD_WRITEABLE:允许其他应用写入该文件
    // MODE_MULTI_PROCESS:多进程模式（允许多进程读写）

    //TODO 这里的保存数据属于覆盖保存，只要键值是一样的，就会直接覆盖在原来之上
    public static void saveData(Context context,String index,String name,String phoneNumber){
        SharedPreferences sharedPreferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);//实例化SharedPreferences对象，只支持当前应用访问
        SharedPreferences.Editor editor = sharedPreferences.edit();//SharedPreferences 本身只能读数据，必须通过 Editor(编辑类) 才能写入 / 修改数据，不支持查询
        editor.putString("name"+index,name);
        editor.putString("phoneNumber"+index,phoneNumber);
        editor.commit();
        //提交编辑操作：将 Editor 中添加的所有键值对（用户名 + 密码）真正写入到 "data.xml" 文件中。
        //⚠️ 关键：如果不调用 commit() 或 apply()，所有 putXXX() 操作都只是暂存在内存中，不会写入文件！
        //- commit()：同步提交（阻塞当前线程，返回布尔值表示是否成功）；
        //- apply()：异步提交（不阻塞线程，无返回值，推荐在主线程使用）
    }

    public static void getData(Context context,String index){
        SharedPreferences sharedPreferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name"+index,null);
        String phoneNumber = sharedPreferences.getString("phoneNumber"+index,null);
        map.put("name"+index,name);
        map.put("phoneNumber"+index,phoneNumber);
    }
}
