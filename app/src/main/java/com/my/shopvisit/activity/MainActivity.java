package com.my.shopvisit.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.shopvisit.R;
import com.my.shopvisit.adapter.myFragmentPageAdapter;
import com.my.shopvisit.fragment.HomeFrament;
import com.my.shopvisit.fragment.MeFrament;
import com.my.shopvisit.fragment.ShopFrament;
import com.my.shopvisit.fragment.TrainFrament;
import com.my.shopvisit.fragment.VisitFrament;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zj on 2017/7/24.
 *
 * 登录之后的主界面
 */

public  class MainActivity extends BaseActivity implements View.OnClickListener {

    private final int TAB_HOME = 0;
    private final int TAB_SHOP = 1;
    private final int TAB_VISIT = 2;
    private final int TAB_TRAIN = 3;
    private final int TAB_ME = 4;
    private int IsTab=0;
    private ViewPager mViewPager;
    private TextView mBack;
    private TextView mTitleName;
    private ImageView mIv_more;
    private ImageView mIv_save;
    private ImageView mIv_change;
    private TextView mTv_home;
    private TextView mTv_shop;
    private TextView mTv_visit;
    private TextView mTv_train;
    private TextView mTv_me;
    List<ImageView> imageViews= new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.main_viewpage);

        mBack = (TextView) findViewById(R.id.title_bar_back);
        mTitleName = (TextView) findViewById(R.id.title_bar_name);
        mIv_more = (ImageView) findViewById(R.id.title_bar_more);
        mIv_save = (ImageView) findViewById(R.id.title_bar_save);
        mIv_change = (ImageView) findViewById(R.id.title_bar_change);

        mTv_home = (TextView) findViewById(R.id.txt_menu_bottom_home);
        mTv_shop = (TextView) findViewById(R.id.txt_menu_bottom_shop);
        mTv_visit = (TextView) findViewById(R.id.txt_menu_bottom_visit);
        mTv_train = (TextView) findViewById(R.id.txt_menu_bottom_train);
        mTv_me = (TextView) findViewById(R.id.txt_menu_bottom_me);

    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(this);
        mIv_more.setOnClickListener(this);
        mIv_save.setOnClickListener(this);
        mIv_change.setOnClickListener(this);

        mTv_home.setOnClickListener(this);
        mTv_shop.setOnClickListener(this);
        mTv_visit.setOnClickListener(this);
        mTv_train.setOnClickListener(this);
        mTv_me.setOnClickListener(this);



    }

    /**
     * 初化数据
     */
    @Override
    protected void initData() {
        List<Fragment> fragments=new ArrayList<>();
        HomeFrament homeFrament=new HomeFrament();
        ShopFrament shopFrament=new ShopFrament();
        VisitFrament visitFrament=new VisitFrament();
        TrainFrament trainFrament=new TrainFrament();
        MeFrament meFrament=new MeFrament();

        fragments.add(homeFrament);
        fragments.add(shopFrament);
        fragments.add(visitFrament);
        fragments.add(trainFrament);
        fragments.add(meFrament);

        FragmentManager fragmentManager=getSupportFragmentManager();
        mViewPager.setAdapter(new myFragmentPageAdapter(fragmentManager,fragments));
        mViewPager.setOffscreenPageLimit(2);
        //设置默认界面
        setTabSelected(mTv_home);
        mViewPager.setCurrentItem(TAB_HOME);
        mTitleName.setText("首页");



        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

               switch (position){
                   case TAB_HOME:
                       imageViews.removeAll(imageViews);
                       setTabSelected(mTv_home);
                       mTitleName.setText("首页");
                       setImgVisibility(imageViews);
                       break;
                   case TAB_SHOP:
                       imageViews.removeAll(imageViews);
                       setTabSelected(mTv_shop);
                       mTitleName.setText("巡店");
                       imageViews.add(mIv_more);
                       imageViews.add(mIv_change);
                       setImgVisibility(imageViews);
                       break;
                   case TAB_VISIT:
                       imageViews.removeAll(imageViews);
                       setTabSelected(mTv_visit);
                       mTitleName.setText("拜访");
                       imageViews.add(mIv_more);
                       setImgVisibility(imageViews);
                       break;
                   case TAB_TRAIN:
                       imageViews.removeAll(imageViews);
                       setTabSelected(mTv_train);
                       mTitleName.setText("培训");
                       setImgVisibility(imageViews);
                       break;
                   case TAB_ME:
                       imageViews.removeAll(imageViews);
                       setTabSelected(mTv_me);
                       mTitleName.setText("个人中心");
                       setImgVisibility(imageViews);
                       break;
               }
                imageViews.clear();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setImgVisibility(List<ImageView> lists){
        mBack.setVisibility(View.GONE);
        mIv_more.setVisibility(View.GONE);
        mIv_save.setVisibility(View.GONE);
        mIv_change.setVisibility(View.GONE);
        for (ImageView view: lists) {
            view.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public void onClick(View v) {
        List<ImageView> imageViews= new ArrayList<>();
        switch (v.getId()){
            case R.id.txt_menu_bottom_home :
                imageViews.removeAll(imageViews);
                setTabSelected(mTv_home);
                mTitleName.setText("首页");
                mViewPager.setCurrentItem(TAB_HOME);
                setImgVisibility(imageViews);
                break;
            case R.id.txt_menu_bottom_shop:
                imageViews.removeAll(imageViews);
                setTabSelected(mTv_shop);
                mTitleName.setText("巡店");
                mViewPager.setCurrentItem(TAB_SHOP);
                imageViews.add(mIv_more);
                imageViews.add(mIv_change);
                setImgVisibility(imageViews);
                break;
            case R.id.txt_menu_bottom_visit:
                imageViews.removeAll(imageViews);
                setTabSelected(mTv_visit);
                mTitleName.setText("拜访");
                mViewPager.setCurrentItem(TAB_VISIT);
                imageViews.add(mIv_more);
                setImgVisibility(imageViews);
                break;
            case R.id.txt_menu_bottom_train:
                imageViews.removeAll(imageViews);
                setTabSelected(mTv_train);
                mTitleName.setText("培训");
                mViewPager.setCurrentItem(TAB_TRAIN);
                setImgVisibility(imageViews);
                break;
            case R.id.txt_menu_bottom_me:
                imageViews.removeAll(imageViews);
                setTabSelected(mTv_me);
                mTitleName.setText("个人中心");
                mViewPager.setCurrentItem(TAB_ME);
                setImgVisibility(imageViews);
                break;
        }
        imageViews.clear();
    }

    //当选中的时候变色,改变底部文字颜色
    public void setTabSelected(TextView textView) {
        mTv_home.setSelected(false);
        mTv_shop.setSelected(false);
        mTv_visit.setSelected(false);
        mTv_train.setSelected(false);
        mTv_me.setSelected(false);
        textView.setSelected(true);
    }
}