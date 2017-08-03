package com.my.shopvisit.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *   请求网络的工具类
 * Created by admin on 2017/8/2.
 */

public class OkHttpManager {
   private static OkHttpManager mOkhttpManager;
    private OkHttpClient mOkHttpClient;
    private Handler okHandler;


   public OkHttpManager(){
     okHandler=new Handler(Looper.getMainLooper());
     mOkHttpClient=new OkHttpClient.Builder()
             .connectTimeout(15, TimeUnit.SECONDS)
             .writeTimeout(20, TimeUnit.SECONDS)
             .readTimeout(20, TimeUnit.SECONDS)
             .build();
   }

   public static OkHttpManager getInstance(){
       if (mOkhttpManager==null){
           synchronized (OkHttpManager.class){
              if (mOkhttpManager==null){
                  mOkhttpManager=new OkHttpManager();
              }
           }
       }
       return mOkhttpManager;
   }

    public static abstract class ResultCallback {
        public abstract void onFailed(Request request, IOException e);

        public abstract void onSuccess(String response);
    }

    public void getNet(String url, ResultCallback resultCallback) {
        Log.i("okhttp","url--"+url);
        Request request = new Request.Builder()
                .url(url)
                .method("GET",null)//此设置默认为get,可以不设置
                .build();
        dealNet(request, resultCallback);
    }

    private void dealNet(final Request request, final ResultCallback resultCallback) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                okHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        resultCallback.onFailed(request, e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = "";
                try {
                    str = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final String finalStr = str;
                okHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        resultCallback.onSuccess(finalStr);

                    }
                });
            }
        });
    }

    public void postNet(String url, ResultCallback resultCallback, Param... param) {
        if (param == null) {
            param = new Param[0];
        }
        FormBody.Builder formBody = new FormBody.Builder();
        for (Param p : param) {
            formBody.add(p.key, p.value);
        }
        RequestBody requestBody = formBody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        dealNet(request, resultCallback);
    }

    public static class Param {
        String key;
        String value;

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

}
