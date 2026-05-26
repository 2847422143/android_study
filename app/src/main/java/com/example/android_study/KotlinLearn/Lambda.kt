package com.example.android_study.KotlinLearn


fun aa() {
    val list = ArrayList<String>()
    list.add("1")
    list.add("2")
    list.add("3")

    //有一种简介的写法
    //但是有个问题，listOf返回的是一个不可变的集合，不能修改元素
    val list1 = listOf("1", "2", "3")
    //创建可变的集合
    val list2 = mutableListOf("1", "2", "3")
    list2.add("4")

    //Set  setOf()  mutableSetOf()  存放不可重复的元素

    //遍历集合
    for (a in list1) println(a)
    for (b in list2) println(b)


    //TODO MAP 键值对的数据结构
    //简化为以下写法 健 to 值
    val map = mapOf("Apple" to 1, "Banana" to 2, "Orange" to 3, "Pear" to 4, "Grape" to 5)
    for ((a, b) in map) println("$a -> $b")

    //maxBy 集合的函数式API ，根据元素长度获取最大值
    val list3 = listOf("Apple", "Banana", "Orange", "Pear", "Grape", "Watermelon")
    val maxLengthFruit = list3.maxBy { it.length }
    println("max length fruit is " + maxLengthFruit)

    //都变成大写
    val newList = list3.map { it.toUpperCase() }
    println(newList)

    //filter 过滤函数，过滤出长度小于等于5的元素
    val newList1 = list.filter { it.length <= 5 }
        .map { it.toUpperCase() }
    for (fruit in newList) {
        println(fruit)
    }
    //any函数用于判断集合中是否至少存在一个元素满足指定条件
    //all函数用于判断集合中是否所有元素都满足指定条件
    val anyResult = list3.any { it.length <= 5 } //true
    val allResult = list3.all { it.length <= 5 } //false
    println("anyResult is " + anyResult + ", allResult is " + allResult)
}
