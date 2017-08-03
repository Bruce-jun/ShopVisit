package com.my.shopvisit.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * Created by zj on 2017/7/25.
 */

public class MySharedPreferences {
    public  static SharedPreferences sp;

    /**
     * @param context 上下文环境
     * @param key     存储结点的名称
     * @param value   存储的值
     */
    public static void putString(Context context, String key, String value){
        if (sp==null){
            sp= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).commit();
    }

    /**
     * @param context  上线稳环境
     * @param key      存储结点的名称
     * @param devalue  默认返回的值
     * @return  返回值
     */
    public  static String getString(Context context,String key,String devalue){
        if (sp==null){
            sp= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return  sp.getString(key,devalue);
    }

    /**
     * @param context 上下文环境
     * @param key     存储结点的名称
     * @param value   存储的值
     */
    public static void putBoolean(Context context,String key,boolean value){
        if (sp==null){
            sp= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();
    }

    /**
     * @param context  上线稳环境
     * @param key      存储结点的名称
     * @param devalue  默认返回的值
     * @return  返回值
     */
    public  static boolean getBoolean(Context context,String key,boolean devalue){
        if (sp==null){
            sp= context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return  sp.getBoolean(key,devalue);
    }

}
