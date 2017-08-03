package com.my.shopvisit.bean;

import org.litepal.crud.DataSupport;

/**
 *
 * Created by zj on 2017/7/24.
 */

public class User extends DataSupport {

    public String name;
    public String password;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String userid;

    public String job;

    public String nickname;

    public String phonenum;

    public int sex;

    public String img;

    public String registdate;

    public String area;

}
