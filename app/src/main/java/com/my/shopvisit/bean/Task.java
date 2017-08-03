package com.my.shopvisit.bean;

import org.litepal.crud.DataSupport;

/**
 *  首页任务详情
 * Created by admin on 2017/7/28.
 */

public class Task  extends DataSupport{

    /**
     * detail : 针对公司新产品Y008的市场调研。需要来店里咨询的客户填写问卷，并留联系方式。问卷已发送至各位邮箱，请下载并打印。
     * executedate : 2016-09-30
     * publishdate : 2016-08-15
     * state : 0
     * title : 新产品Y008调研
     */
    public String detail;
    public String executedate;
    public String publishdate;
    public int state;
    public String title;

}
