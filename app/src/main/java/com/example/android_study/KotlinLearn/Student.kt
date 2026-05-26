package com.example.android_study.KotlinLearn

//: Person()表示继承了这个类
//class Student : Person() {
//    var sno = ""
//    var grade = 0
//    init {
//        println("sno is " + sno)
//        println("grade is " + grade)
//    }
//}
//todo Person类后面的一对空括号表示Student类的主构造函数在初始化的时候会调用Person类的无参数构造函数

//当一个类没有显式地定义主构造函数且定义了次构 造函数时，它就是没有主构造函数的。
//既然没有主构造函数，继承 Person类的时候也就不需要再加上括号了
//class Student : Person {
//    constructor(name: String, age: Int) : super(name, age) {
//    }
//}


//另一种写法，这种写法，调用的时候就必须有参数（也就是没有了无参构造函数）
//这里如果继承的类是有参构造函数，父类的参数也需要子类带上，但是不能够声明val,不然就成了子类的参数了

//Java中继承使用的关键字是extends，实现接口使用的关键字是implements，而Kotlin中统一使用冒号，中间用逗号进行分隔
class Student(val sno: String, val grade: Int, name: String, age: Int) : Person(name, age) , Study {
    //constructor 表示次构造函数，次构造函数的调用都需要使用this关键字调用主构造函数
    constructor(name: String, age: Int) : this("", 0, name, age) {
    }
    constructor() : this("", 0) {
    }
    init {
        println("sno is " + sno)
        println("grade is " + grade)
    }

    override fun read() {
        println("read")
    }

    override fun write() {
        println("write")
    }
}
