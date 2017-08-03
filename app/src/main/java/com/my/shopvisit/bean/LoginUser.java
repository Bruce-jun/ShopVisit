package com.my.shopvisit.bean;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by zj on 2017/7/24.
 */

public class LoginUser {

    /**
     * code : 0
     * msg : 登录成功
     * body : {"userid":"num01","job":" 经理 ","nickname":"张华","phonenum":"18913145210","sex":0,"img":"visitshop/img/user/head.png","registdate":"2016-10-20","area":" 华中地区 "}
     */
    @SerializedName("msg")
    public String msg;
    @SerializedName("body")
    public BodyBean body;

    public  class BodyBean {
        /**
         * userid : num01
         * job :  经理
         * nickname : 张华
         * phonenum : 18913145210
         * sex : 0
         * img : visitshop/img/user/head.png
         * registdate : 2016-10-20
         * area :  华中地区
         */
        @SerializedName("userid")
        public String userid;
        @SerializedName("job")
        public String job;
        @SerializedName("nickname")
        public String nickname;
        @SerializedName("phonenum")
        public String phonenum;
        @SerializedName("sex")
        public int sex;
        @SerializedName("img")
        public String img;
        @SerializedName("registdate")
        public String registdate;
        @SerializedName("area")
        public String area;


    }
}
