package com.example.android_study.EventBus;

/**
 * 普通事件：传递文本消息
 * 用于常规的事件传递，携带简单的文本消息：
 */
public class MessageEvent {
    private String message;

    // 构造方法
    public MessageEvent(String message) {
        this.message = message;
    }

    // Getter 方法
    public String getMessage() {
        return message;
    }
}