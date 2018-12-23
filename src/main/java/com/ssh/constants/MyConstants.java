package com.ssh.constants;

/**
 * 常量管理
 * 主要管理3类常量
 * 1.以后可能会变的
 * 2.经常用到的
 * 3.常量值的含义不明确的
 */
public class MyConstants {
    //爬取状态
    public static final String status0="未爬取";
    public static final String status1="爬取中...";
    public static final String status2="停止爬取";
    public static final String status3="爬取完成";
    public static final String status4="初始化";
    //flag
    public static final String one="1";
    //线程池大小
    public static final int poolSize=24;
    //空值
    public static final String empty="";
    //setInterval的时间间距
    public static final int interval=1;
    //爬取速度的单位
    public static final String speedUnit=" 次/秒";
    //更新视图状态
    public static final String updateViewsStatus0="更新中";
    public static final String updateViewsStatus11="更新完成";
    public static final String updateViewsStatus1="正在更新视图1";
    public static final String updateViewsStatus2="正在更新视图2";
    public static final String updateViewsStatus3="正在更新视图3";
    public static final String updateViewsStatus4="正在更新视图4";
    public static final String updateViewsStatus5="正在更新视图5";
    //charset
    public static final String charset="UTF-8";
    //日期格式化
    public static final String dateFormatString="yyyy年MM月dd日 HH时mm分ss秒";
}
