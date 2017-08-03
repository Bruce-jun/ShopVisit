package com.my.shopvisit.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 *
 * Created by admin on 2017/7/26.
 */

public class HttpUtils {

    public  static  void sendOkhttpRequest(String url, okhttp3.Callback callback){
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
