package com.my.shopvisit.bean;

import java.util.List;

/**
 *
 * Created by admin on 2017/7/31.
 */

public class ShopHistory {

    /**
     * code : 0
     * msg : 历史巡店查询成功
     * page : 1
     * datelist : [{"id":1,"visitdate":"2016-10-20","shopid":"WFSF75","shoplocation":"中国北京市海淀区成府路207号","userid":"num01","shoplevel":"5;5;5","feedback":"店面整洁，人员精神饱满，没有发现问题","name":"北京新中关购物中心店","imgpath":"/visitshop/img/visit/2016-10-20/","imgname":"1476951920804_1.jpg;1476951920805_2.jpg"}]
     */

    public int code;
    public String msg;
    public int page;
    public List<Historys> datelist;


}
