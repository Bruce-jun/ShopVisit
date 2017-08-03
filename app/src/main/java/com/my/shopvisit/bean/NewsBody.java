package com.my.shopvisit.bean;

import org.litepal.crud.DataSupport;

/**
 *   首页资讯
 * Created by admin on 2017/7/31.
 */

public class NewsBody extends DataSupport{

    /**
     * title : 华为联想全球化启示：如何在海外构建中国品牌
     * summary : 腾讯科技
     * imgurl : http://mat1.gtimg.com/tech/00Jamesdu/2014/index/remark/2.png
     * detail : http://tech.qq.com/a/20151123/008196.htm
     */
    public String title;
    public String summary;
    public String imgurl;
    public String detail;

}
