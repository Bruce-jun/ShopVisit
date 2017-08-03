package com.my.shopvisit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.my.shopvisit.R;
import com.my.shopvisit.adapter.ShopHistoryRvAtapter;
import com.my.shopvisit.bean.Historys;
import com.my.shopvisit.bean.ShopHistory;
import com.my.shopvisit.net.OkHttpManager;
import com.my.shopvisit.utils.Constant;
import com.my.shopvisit.utils.ShowToast;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * 巡店界面
 * Created by zj on 2017/7/26.
 */

public class ShopFrament extends BaseFrament implements
                                XRecyclerView.LoadingListener, View.OnClickListener,
                                TextView.OnEditorActionListener {

    private View mView;
    private EditText mEt_search;
    private XRecyclerView mXRecyclerView;
    private TextView mShop_none;
    private RelativeLayout mShop_progres;
    private RelativeLayout mShop_refresh;
    private static List<Historys> mHistoryses; //历史巡店数据集合
    private int mPagenum;
    private ShopHistoryRvAtapter mAtapter;
    private String mUserid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = View.inflate(mContext, R.layout.shop_fragment,null);
        initview();
        getHistory();
        return mView;
    }

    /**
     * 初始化控件
     */
    private void initview() {
        mPagenum=1;
        mEt_search = (EditText) mView.findViewById(R.id.shop_search_et);
        mEt_search.setOnEditorActionListener(this);
        mXRecyclerView = (XRecyclerView) mView.findViewById(R.id.shop_XRV);
        mShop_none = (TextView) mView.findViewById(R.id.shop_none);
        mShop_progres = (RelativeLayout) mView.findViewById(R.id.shop_progress);
        mShop_refresh = (RelativeLayout) mView.findViewById(R.id.shop_refresh);

        //配置XRecyclerView的信息
        //设置线性列表展示
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        //设置加载更多与刷新
        mXRecyclerView.setLoadingListener(this);
        //设置加载样式
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);

        //设置空布局
        View emptyView = mShop_none;
        emptyView.setOnClickListener(this);
        mXRecyclerView.setEmptyView(emptyView);

        if (mHistoryses==null){
            mHistoryses=new ArrayList<>();
        }
        mAtapter = new ShopHistoryRvAtapter(mContext,mHistoryses);
        mXRecyclerView.setAdapter(mAtapter);


    }

    /**
     * 请求服务器获得历史巡店数据
     */
    private void getHistory() {
        mShop_progres.setVisibility(View.VISIBLE);
        mUserid = "num01";
        String url = Constant.HistroyShop+"?userid="+ mUserid +"&pagenum="+ mPagenum;
        OkHttpManager.getInstance().getNet(url, new OkHttpManager.ResultCallback() {
            @Override
            public void onFailed(Request request, IOException e) {
                getHistoryFailure();
            }

            @Override
            public void onSuccess(String response) {
                getHistorySuccess(response);
            }
        });
    }

    /**
     * 联网失败则从本地缓存获取
     */
    private void getHistoryFailure() {
        List<Historys> list=DataSupport.findAll(Historys.class);
    }

    /**
     * @param result  服务器的返回
     */
    private void getHistorySuccess(String result) {
        if (result!=null&& ! "".equals(result)){
            Gson gson=new Gson();
            ShopHistory shopHistory=gson.fromJson(result,ShopHistory.class);
            if (mHistoryses == null) {
                mHistoryses = new ArrayList<Historys>();
            }
            //如果是第一页数据，则把之前数据清空
            if (mPagenum == 1){
                mHistoryses.clear();
            }
            if (shopHistory.datelist.size()==0){
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowToast.showToastShort(mContext,"没有更多数据");
                    }
                });
            }else {
                mHistoryses.addAll(shopHistory.datelist);
            }
            mPagenum++;
            //更新视图
            onLoad();
            mShop_progres.setVisibility(View.GONE);
            mAtapter.notifyDataSetChanged();
        }
    }
    /**
     * RecyclerView的下拉刷新
     */
    @Override
    public void onRefresh() {
        mPagenum=1;
        getHistory();
    }

    /**
     * RecyclerView的上拉加载
     */
    @Override
    public void onLoadMore() {
        getHistory();
    }
    /**
     * 结束上下拉刷新
     */
    private void onLoad() {
        mXRecyclerView.refreshComplete();
        mXRecyclerView.loadMoreComplete();
    }

    /**
      * @param v 点击的控件
     *
     */
    @Override
    public void onClick(View v) {



    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mHistoryses.clear();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId== EditorInfo.IME_ACTION_SEARCH){
            hideKeyboard();
            String shop_name = mEt_search.getText().toString().trim();
            mPagenum=1;
            mShop_progres.setVisibility(View.VISIBLE);
            String url = Constant.HistroyShop+"?userid="+mUserid+"&pagenum="+mPagenum+"&keyword="+shop_name;
            OkHttpManager.getInstance().getNet(url, new OkHttpManager.ResultCallback() {
                @Override
                public void onFailed(Request request, IOException e) {
                    getHistoryFailure();
                }

                @Override
                public void onSuccess(String response) {
                    getHistorySuccess(response);
                }
            });
            return true;
        }
        return false;
    }

    /**
     * 隐藏软件盘
     */

    public void hideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(mActivity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
