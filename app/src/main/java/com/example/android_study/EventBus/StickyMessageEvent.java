package com.example.android_study.EventBus;

/**
 * 粘性事件：传递粘性消息
 * 用于粘性事件传递（发送事件后再注册订阅者，仍能接收该事件）：
 */
public class StickyMessageEvent {
    private String stickyMessage;

    public StickyMessageEvent(String stickyMessage) {
        this.stickyMessage = stickyMessage;
    }

    public String getStickyMessage() {
        return stickyMessage;
    }
}