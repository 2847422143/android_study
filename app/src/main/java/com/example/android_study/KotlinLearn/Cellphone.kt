package com.example.android_study.KotlinLearn

//data 关键字表示这个类是数据类，kotlinKotlin会根据主构造函数中的
// 参 数帮你将equals()、hashCode()、toString()等固定且无实际逻辑意义的方法自动生成
data class Cellphone(val brand:String,val proce:Double)