package com.my.shopvisit.utils;

import android.content.Context;
import android.widget.Toast;

/**
 *
 * Created by zj on 2017/7/24.
 */

public class ShowToast {

    public static void showToastShort(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
    public static void showToastLong(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_LONG).show();
    }
}
