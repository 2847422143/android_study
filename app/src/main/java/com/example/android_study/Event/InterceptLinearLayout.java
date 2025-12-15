package com.example.android_study.Event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

// 自定义LinearLayout，用于演示事件拦截
public class InterceptLinearLayout extends LinearLayout {
    private static final String TAG = "MainActivity";
    private boolean isIntercept = true; // 是否拦截btn_touch的事件

    public InterceptLinearLayout(Context context) {
        super(context);
    }

    public InterceptLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //事件分发的入口方法
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        boolean handled = false;
        Log.d(TAG, "接收到事件：" + replaceAction(ev.getAction()) );

        // 步骤1：判断是否拦截事件（仅 ViewGroup 有 onInterceptTouchEvent）
        intercepted = onInterceptTouchEvent(ev); // 判断是否需要拦截


        // 步骤2：如果不拦截 → 向下分发到子 View（如 btn_touch）
        if (!intercepted) {
            Log.d(TAG, "不拦截：" + replaceAction(ev.getAction()) );
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                // 递归调用子 View 的 dispatchTouchEvent
                if (child.dispatchTouchEvent(ev)) {
                    handled = true;
                    break;
                }
            }
        }

        // 步骤3：如果拦截 / 子 View 不消费 → 交给自身 onTouchEvent 处理
        if (!handled) {
            Log.d(TAG, "拦截：" + replaceAction(ev.getAction()) );
            handled = onTouchEvent(ev); // 你重写的消费方法
        }

        return handled;
    }

    // ViewGroup核心拦截方法：返回true则拦截事件（不向下传递给子View）
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        String actionName = replaceAction(action);
        // 只拦截btn_touch区域的事件（可选：精准拦截）
        boolean intercept = false;
        //TODO 一旦某个 ViewGroup 拦截了事件序列的起点（ACTION_DOWN），
        // 那么整个事件序列（后续的 MOVE/UP）都会直接交由该 ViewGroup 处理，不会再向下传递给子 View，也不会再触发 ViewGroup 的 onInterceptTouchEvent。
//        if (isIntercept && action == MotionEvent.ACTION_DOWN) {
//            intercept = true; // 拦截DOWN事件，后续事件都不会传给btn_touch
//            Log.d(TAG, "InterceptLinearLayout 拦截了事件：" + actionName);
//        }
        if (isIntercept && action == MotionEvent.ACTION_MOVE) {
            intercept = true; // 拦截MOVE事件
            Log.d(TAG, "InterceptLinearLayout 拦截了事件：" + actionName);
        }

        return intercept;
    }

    private String replaceAction(int action){
        String actionName;
        switch (action) {
            case MotionEvent.ACTION_DOWN :
                actionName = "ACTION_DOWN（按下）";
                break;
            case MotionEvent.ACTION_MOVE :
                actionName ="ACTION_MOVE（移动）";
                break;
            case MotionEvent.ACTION_UP :
                actionName = "ACTION_UP（抬起）";
                break;
            default :
                actionName = "UNKNOWN";
                break;
        }
        return actionName;
    }

    // 拦截后，事件会进入ViewGroup的onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "InterceptLinearLayout 消费拦截的事件：" + replaceAction(event.getAction()));
        return true; // 消费拦截的事件
    }


    // 外部控制是否拦截
    public void setIntercept(boolean intercept) {
        isIntercept = intercept;
    }
}