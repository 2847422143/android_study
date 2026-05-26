package com.example.android_study.KotlinLearn

interface Study {
    fun read()

    //如果方法中加入了函数体，就表示这个方法默认实现了
    fun write(){
        println("write")
    }
}