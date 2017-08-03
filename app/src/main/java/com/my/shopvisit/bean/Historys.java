package com.my.shopvisit.bean;

import org.litepal.crud.DataSupport;

/**
 *
 * Created by admin on 2017/7/31.
 */

public class Historys extends DataSupport {

    /**
     * id : 1
     * visitdate : 2016-10-20
     * shopid : WFSF75
     * shoplocation : 中国北京市海淀区成府路207号
     * userid : num01
     * shoplevel : 5;5;5
     * feedback : 店面整洁，人员精神饱满，没有发现问题
     * name : 北京新中关购物中心店
     * imgpath : /visitshop/img/visit/2016-10-20/
     * imgname : 1476951920804_1.jpg;1476951920805_2.jpg
     */

    public int id;
    public String visitdate;
    public String shopid;
    public String shoplocation;
    public String userid;
    public String shoplevel;
    public String feedback;
    public String name;
    public String imgpath;
    public String imgname;

}
