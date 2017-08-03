package com.my.shopvisit.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.my.shopvisit.R;
import com.my.shopvisit.adapter.HomeNewsRVAdapter;
import com.my.shopvisit.adapter.HomeTaskRVAdapter;
import com.my.shopvisit.adapter.HomeViewpageAdapter;
import com.my.shopvisit.bean.Ann;
import com.my.shopvisit.bean.HomeAnn;
import com.my.shopvisit.bean.HomeNew;
import com.my.shopvisit.bean.HomeTask;
import com.my.shopvisit.bean.NewsBody;
import com.my.shopvisit.bean.Task;
import com.my.shopvisit.utils.Constant;
import com.my.shopvisit.utils.HttpUtils;
import com.my.shopvisit.view.NewsScrollView;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.litepal.crud.DataSupport.findAll;

/**
 *
 * Created by zj on 2017/7/26.
 */

public class HomeFrament extends BaseFrament {

    private View mView;
    private ViewPager mViewPager;
    private Thread mThread;
    private boolean mIsRunning;
    //公告的展示集合
    private List<View> mViews;
    private HomeViewpageAdapter mAdapter;
    private LinearLayout mPointLayout;
    private Timer timer;//计时器

    // 创建记时器发送图片轮播消息
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
        }
    };

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                    break;
                default:
                    break;
            }
        }
    };
    private TextView mNewShop;
    private TextView mNewTrain;
    private TextView mNewVisit;
    private TextView mMoreTask;
    private TextView mMoreNews;
    private RecyclerView mTaskRV;
    private RecyclerView mNewsRV;
    private HomeTaskRVAdapter mTaskRVAdapter;
    private NewsScrollView mHome_scrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = View.inflate(mContext, R.layout.home_fragment,null);
        initView();
        initData();
        return mView;
    }



    private void initView() {
        mViewPager = (ViewPager) mView.findViewById(R.id.home_viewpage);
        mPointLayout = (LinearLayout) mView.findViewById(R.id.home_viewpage_point);
        mHome_scrollView = (NewsScrollView) mView.findViewById(R.id.home_scrollView);
        mNewShop = (TextView) mView.findViewById(R.id.home_new_shop);
        mNewTrain = (TextView) mView.findViewById(R.id.home_new_train);
        mNewVisit = (TextView) mView.findViewById(R.id.home_new_visit);

        mMoreTask = (TextView) mView.findViewById(R.id.home_task_more_tv);
        mMoreNews = (TextView) mView.findViewById(R.id.home_news_more_tv);

        mTaskRV = (RecyclerView) mView.findViewById(R.id.home_task_rv);
        mNewsRV = (RecyclerView) mView.findViewById(R.id.home_news_rv);
        mTaskRV.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mNewsRV.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mTaskRV.setNestedScrollingEnabled(false);
//        mNewsRV.setNestedScrollingEnabled(false);
        mHome_scrollView.setXRecyclerView(mNewsRV);

    }

    private void initData() {
        if (mViews == null) {
            mViews = new ArrayList<View>();
        }
        getImageFormInternet();//获取公告
        getTaskFormInternet();//获取任务信息
        getNewsFormInternet();//获取资讯信息

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int p=position % mViews.size();
                for (int i=0;i<mViews.size();i++){
                    ImageView image = (ImageView) mPointLayout.getChildAt(i);
                    if (i==p){
                        image.setSelected(true);
                    }else {
                        image.setSelected(false);
                    }

                }

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 获取资讯系信息
     */
    private void getNewsFormInternet() {
        String url= Constant.Info+"?pagenum="+1+"&type="+0;
        HttpUtils.sendOkhttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                   if (response.isSuccessful()){
                       String result=response.body().string();
                       getNewSuccess(result);
                   }
            }


        });
    }

    /**
     * @param result 任务信息
     *               资讯信息成功
     */
    private void getNewSuccess(String result) {
        if (result!=null && !result.equals("")){
            Gson gson=new Gson();
            HomeNew homeNew=gson.fromJson(result,HomeNew.class);
            final List<NewsBody> newsBodies=homeNew.body;
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateNewsShow(newsBodies);
                }
            });

        }
    }

    private void updateNewsShow(List<NewsBody> newsBodies) {
        int count=3;
        HomeNewsRVAdapter homeNewsRVAdapter=new HomeNewsRVAdapter(mContext,newsBodies,newsBodies.size());
        mNewsRV.setAdapter(homeNewsRVAdapter);
    }

    /**
     * 获取任务信息
     */
    private void getTaskFormInternet() {
        String url= Constant.Task+"?pagenum="+1+"&type="+0;
        HttpUtils.sendOkhttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getTaskFailure();
            }



            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    getTaskSuccess(result);
                }
            }
        });
    }

    private void getTaskFailure() {
        List<Task>  tasks= DataSupport.findAll(Task.class);
        updateTaskShow(tasks);
    }
    /**
     * @param result  返回的 task 信息
     */
    private void getTaskSuccess(String result) {
        if (result!=null && !result.equals("")){
            Gson gson=new Gson();
            HomeTask homeTask=gson.fromJson(result,HomeTask.class);
            if ( homeTask.body != null) {
                final List<Task> tasks = homeTask.body;
                updateTaskShow(tasks);
                DataSupport.deleteAll(Task.class);
                DataSupport.saveAll(tasks);
            }
        }

    }

    /**
     * @param tasks 更新最新任务视图
     */
    private void updateTaskShow(List<Task> tasks) {
        int count=3;
        mTaskRVAdapter = new HomeTaskRVAdapter(mActivity,tasks,count);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTaskRV.setAdapter(mTaskRVAdapter);
                mTaskRVAdapter.notifyDataSetChanged();
            }
        });

    }

    /**
     * 从服务器获取公告数据
     */
    public void getImageFormInternet() {
        String url= "http://10.0.2.2:8080/visitshop/announcement";
        HttpUtils.sendOkhttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                   getAnnFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    getAnnSuccess(result);
                }
            }
        });
    }


    /**
     * 从服务端获取公告信息失败
     */
    public void getAnnFailure() {
        //从数据库中获取数据
        final List<Ann> imgs_dblist = findAll(Ann.class);
        if (imgs_dblist != null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateAnnShow(imgs_dblist);
                }
            });

        }
    }

    /**
     * @param imgs
     * 从服务端获取公告信息
     */
    private void getAnnSuccess(String imgs) {
        //服务端返回有效数据则展示，没有不做处理
        if (imgs!=null && !"".equals(imgs)){
            Gson gson=new Gson();
            HomeAnn ann=gson.fromJson(imgs,HomeAnn.class);
            List<Ann> imgs_list=ann.body;
            if (imgs_list == null) {
                imgs_list = new ArrayList<Ann>();
            }
            final List<Ann> finalImgs_list = imgs_list;
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateAnnShow(finalImgs_list);
                }
            });
            //展示公告
            if (imgs_list.size() > 0) {
                //从数据库清除数据保存
                DataSupport.deleteAll(Ann.class);
                //添加新数据到数据库
                DataSupport.saveAll(imgs_list);

            }
        }

    }

    /**
     * @param imgs_list
     * 根据公告地址动态展示图片
     */
    private void updateAnnShow(List<Ann> imgs_list) {
        mViews.clear();
        int count=imgs_list.size();
        if (count>0){
            for (int i=0;i<count;i++){
                String url="http://10.0.2.2:8080"+imgs_list.get(i).imgUrl;
                ImageView imageView=new ImageView(mActivity);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(mActivity).load(url).into(imageView);
                mViews.add(imageView);
            }
        }
        //添加指示器下标点
        initPoint();
        mAdapter = new HomeViewpageAdapter(mActivity,mViews);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(4000000);
//        mAdapter.notifyDataSetChanged();
        if (timer==null){
            timer=new Timer();
            timer.schedule(task, 0, 5000);
        }
    }

    /**
     * 添加指示器下标
     */

    private void initPoint() {
       mPointLayout.removeAllViews();
       for (int i=0;i<mViews.size();i++){
           ImageView img = new ImageView(mActivity);
            LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin=5;
            params.rightMargin=5;
            img.setLayoutParams(params);
            img.setImageResource(R.drawable.home_viewpage_point);
            if (i==0){
                img.setSelected(true);
            }
            mPointLayout.addView(img);
       }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
