package com.my.shopvisit.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 *
 * Created by admin on 2017/8/2.
 */

public class NewsScrollView extends ScrollView {
    public NewsScrollView(Context context) {
        this(context,null);
    }

    public NewsScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NewsScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    static ScreenListener mListener;
    int mMaxScreenY;
    private RecyclerView mTaskRecyclerView;

    public  void setXRecyclerView(RecyclerView recyclerView){
        mTaskRecyclerView=recyclerView;
        initScreenListener();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (getScrollY()>=getMaxScrollAmount()-20){
            if (mMaxScreenY == 0) {
                mMaxScreenY = mListener.getMaxScreenY();
            }
            boolean mIsInBottomArea = ev.getRawY() > mMaxScreenY;
            if (mIsInBottomArea) {
                //不再截断,将滑动事件交给子view处理
                return false;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    public interface ScreenListener {
        int getMaxScreenY(); //获取能滑动的最大Y坐标
    }
    public static void setScreenListener(ScreenListener listener){
        mListener=listener;
    }

    private void initScreenListener() {
        NewsScrollView.setScreenListener(new NewsScrollView.ScreenListener() {
            @Override
            public int getMaxScreenY() {
                int height = mTaskRecyclerView.getHeight();
                int[] location = new int[]{0, 0};
                mTaskRecyclerView.getLocationOnScreen(location);
                return location[1] + height;
            }
        });
    }


}
