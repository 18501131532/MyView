package com.example.duanxiaqingqq.myapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class MyRecyclerView extends RecyclerView {

    private int mScaledEdgeSlop;
    private int mPointerId = -1;
    private int mLastTouchX;
    private int mLastTouchY;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mScrollState;

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //得到视图配置
        //ViewConfiguration      是系统中关于视图的各种特性的常量记录对象。其中包含各种基础数据
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        //getScaledEdgeSlop()    是一个距离，表示滑动的时候，手的移动要大于这个距离才开始移动控件
        mScaledEdgeSlop = viewConfiguration.getScaledEdgeSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (e == null) {
            return false;
        }
        /*getActionMasked
          返回的是具体的响应事件，没有手指下标的信息。
          可以使用getActionIndex来返回和手指动作相关的下标，
          返回的值：比如：ACTION_DOWN，ACTION_POINTER_DOWN。*/
        int actionMasked = e.getActionMasked();
        /*getActionIndex
          返回的是如果调用getActionMasked,返回ACTION_POINTER_DOWN或者ACTION_POINTER_UP,
          这个方法返回的是ACTION_POINTER_DOWN，ACTION_POINTER_UP相关的下标，
          这个下标可能会在getPointerId，getX,getY,getPressed,getSize中用到
          来获取手指的关于按下或者抬起的信息*/
        int actionIndex = e.getActionIndex();
        /*ACTION_DOWN: 表示用户开始触摸.
          ACTION_MOVE: 表示用户在移动(手指或者其他)
          ACTION_UP:表示用户抬起了手指
          ACTION_CANCEL:表示手势被取消了,一些关于这个事件类型的讨论
          ACTION_POINTER_DOWN:多点触摸动作.
          ACTION_POINTER_UP:多点离开动作*/
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                //getPointerId     获取一个pointer的唯一标识符ID，在手指按下和抬起之间ID始终不变
                mPointerId = e.getPointerId(0);
                // 获取第1个触摸点触摸的中间区域相对当前 View 的 X 轴坐标
                mInitialTouchX = mLastTouchX = (int) (e.getX() + 0.5f);
                // 获取第1个触摸点触摸的中间区域相对当前 View 的 Y 轴坐标
                mInitialTouchY = mLastTouchY = (int) (e.getY() + 0.5f);
                return super.onInterceptTouchEvent(e);
            case MotionEvent.ACTION_POINTER_DOWN:
                int pointerId = e.getPointerId(actionIndex);
                // 获取第pointerIndex个触控点触摸的中间区域相对当前 View 的 X 轴坐标
                mInitialTouchX = mLastTouchX = (int) (e.getX(actionIndex) + 0.5f);
                // 获取第pointerIndex个触控点触摸的中间区域相对当前 View 的 Y 轴坐标
                mInitialTouchY = mLastTouchY = (int) (e.getY(actionIndex) + 0.5f);
                return super.onInterceptTouchEvent(e);
            case MotionEvent.ACTION_MOVE: {
                int index = e.findPointerIndex(mPointerId);
                if (index > 0) {
                    return false;
                }
                int x = (int) (e.getX(index) + 0.5f);
                int y = (int) (e.getY(index) + 0.5f);
                if (mScrollState != SCROLL_STATE_DRAGGING) {
                    int dx = x - mInitialTouchX;
                    int dy = y - mInitialTouchY;
                    boolean startScroll = false;
                    if (getLayoutManager().canScrollHorizontally() && Math.abs(dx) > mScaledEdgeSlop
                            && (Math.abs(dx) > Math.abs(dy))) {
                        startScroll = true;
                    }
                    if (getLayoutManager().canScrollVertically() && Math.abs(dy) > mScaledEdgeSlop
                            && (Math.abs(dy) > Math.abs(dx))) {
                        startScroll = true;
                    }
                    return startScroll && super.onInterceptTouchEvent(e);
                }
            }
            return super.onInterceptTouchEvent(e);
            default:
                return super.onInterceptTouchEvent(e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }
}
