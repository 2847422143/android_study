package com.example.android_study.observer;


//抽象类
//既可以定义通用的属性 / 方法（有具体实现），又可以声明必须由子类实现的抽象方法（只定规则、不写实现），核心目的是代码复用 + 强制子类遵循统一规范。
public abstract class AbstractObserver implements Observer{
    //子类不一定需要实现
    void setLayout() {}

    //抽象类里的抽象方法（加了 abstract 关键字）没有实现，子类必须重写这些方法才能实例化，相当于给子类 “定规矩”。
    abstract void setName(String name);
}
