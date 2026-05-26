package com.example.android_study.KotlinLearn

import kotlin.math.max

fun aaa(){

    val a = 10 //val 表示不可变 相当与java中的final
    var b = 20 //var 表示可变  (Kotlin拥有)

    println("add = "+ add(a, b))
    println("max = "+ max1(a, b))

    //显示声明变量类型
    val a1:Int = 10
    val b1:String = "Hello"
    //kotlin中的变量类型也是和java有区别，具体可以看res/drawable中的img_1.png
    //但是基本上都是java变量类型的首字母大写就是kotlin 的变量类型

    b = 30
    println("add = "+ add(a, b))
    println("max = "+ max1(a, b))

    val person = Person("kotlin",18)
    person.eat()

    val student = Student("kotlin",18) //次构造函数
    val student1 = Student()//次构造函数
    val student2 = Student("a123", 5, "Jack", 19)//主构造函数

    student.read()

    doStudy(student)
    val cellphone1 = Cellphone("Samsung", 1299.99)
    val cellphone2 = Cellphone("Samsung", 1299.99)
    println(cellphone1)
    println("cellphone1 equals cellphone2 " + (cellphone1 == cellphone2))

    //单例类的调用，看上去和静态方法的调用一样
    //实际上kotlin会在背后自动创建一个Sinleton的实例，并确保之后一个实例
    Singleton.aaaa()
}

//doStudy()函数接收一个Study类型的参数，由于Student类实现了Study接口，因此Student类的实例是可以传递给
//doStudy()函数的，接下来我们调用了Study接口的函数，
//这种就叫作面向接口编程，也可以称为多态。
fun doStudy(study: Study){
    study.read()
    study.write()
}

//Todo 函数
//这是一个函数也是方法，用于计算两个整数的和，返回Int
fun add(a:Int,b:Int):Int{
    return a + b
}
fun max1(a:Int,b:Int):Int{
    return max(a,b)
}
//当函数体只有一行代码时，还可以进行缩写
fun add1(a:Int,b:Int) = a + b
fun max2(a:Int,b:Int) = max(a,b)

//Todo 逻辑控制

//todo if
fun max3(a:Int,b:Int):Int{
    var value = 0
    if(a>b){
        value = a
    } else {
        value = b
    }
    return value;
}
//可以简化为 (kotlin中if语句是有返回值的)
fun max4(a: Int,b: Int):Int{
    val value = if (a > b)
        a
    else
        b
    //因为这里值对value 做了一次赋值，所以直接可以设置成val
    return value
}
//这里也可以直接压缩成一行代码
fun max5(a: Int,b: Int):Int = if(a>b) a else b

//todo when
//when 语句也是有返回值的，相当于java中的swich，但是不用break
fun getName(a:Int):String = when(a) {
    1 -> "a"
    2 -> "b"
    3 -> "c"
    else -> "c"
}
//并且when语句还可以自己判断类型，惊醒类型匹配
fun checkNumber(number: Number){
    when(number){
        is Int -> println("Int")
        is Double -> println("Double")
        else -> println("Unknown")
    }
}
//when 还有一种不需要参数的写法
fun checkNumber2(number: Number){
    when{
        number is Int -> println("Int")
        number is Double -> println("Double")
        else -> println("Unknown")
    }
}
fun getName1(a:Int):String = when {
   a == 1 -> "a"
   a == 2 -> "b"
   a == 3 -> "c"
    else -> "c"
}

//todo 循环语句
//todo for-in
val orgen = 0..10 //表示一个区间 [0,10]
val orgen1 = 0 until 10 //表示[0,10)
fun asc(){
    for(i in orgen){//in 中 i默认都是区间的第一个值
        println(i)
    }
}
//step 表示 递增的数字 i+2  默认是1
fun asc1(){
    for(i in orgen step 2){
        println(i)
    }
}
//降序 downTo关键字 ,也可以通过step去控制降幅
fun asc2(){
    for(i in 10 downTo 1){
        println(i)
    }
}