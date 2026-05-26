package com.example.android_study.KotlinLearn

//todo 类
//kotlin 默认非抽象类都不可继承，需要添加open关键字告诉kotlin编译器，它是可继承的
//open class Person {
//    var name = ""
//    var age = 0
//    fun eat() {
//        println(name + " is eating. He is " + age + " years old.")
//    }
//}

open class Person(val name:String, var age:Int) {
    fun eat() {
        println(name + " is eating. He is " + age + " years old.")
    }
}