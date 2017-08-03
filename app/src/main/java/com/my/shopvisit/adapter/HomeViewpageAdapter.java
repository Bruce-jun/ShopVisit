package com.my.shopvisit.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 *
 * Created by zj on 2017/7/26.
 */

public class HomeViewpageAdapter extends PagerAdapter {
    List<View> mViews;
    Context mContext;
    public  HomeViewpageAdapter(Context context,List<View> imgUrls){
        mViews=imgUrls;
        mContext=context;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int newPosition = position % mViews.size();
        ImageView imageView = (ImageView) mViews.get(newPosition);
        // a. 把View对象添加到container中
        container.addView(imageView);
        // b. 把View对象返回给框架, 适配器
        return imageView; // 必须重写, 否则报异常
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
