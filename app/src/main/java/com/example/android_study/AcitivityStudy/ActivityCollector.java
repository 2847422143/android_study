package com.example.android_study.AcitivityStudy;

import android.app.Activity;

import java.util.ArrayList;

public class ActivityCollector {
    public static ArrayList<Activity> activityList= new ArrayList<>();
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }
    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }
    public static void finishAll(){
        for (Activity activity:activityList){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        //killProcessy 用于杀死一个进程
        //通过android.os.Process.myPid()获取当前的进程号id
        //注意只能杀死当前程序的进程，不能杀死其他程序的进程
    }
}
