package com.ssh.util;

import com.ssh.constants.MyConstants;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class GlobelData {
    //爬虫相关
    public static String status= MyConstants.status0;//0:未爬取 1：爬取中 2：爬取完成 3：停止爬取
    public static long startTime=0;//开始时间
    public static long endTime=0;//停止时间
    public static String duration="";//已爬取时间

    public static int dataCount=0;//当前爬取数据总数
    public static String crawlerSpeed="";//爬取速度
    public static int requestTimes=0;//请求次数

    public static int realThreadCount=0;//当前获取的线程池中的线程数
    public static int lastThreadCount=0;//之前获取的线程池中的线程数
    public static int compareTimes=0;//让实际和预期多比较几次，以得到更准确的结果

    public static String currentFirstRequestThread="";//当前执行的第一页请求标识

    public static int unuseRequestCount=0; //无效请求数
    public static int unuseTaskCount=0;//无效任务数
    public static int executedTaskCount=0;//已执行任务数


    //分页相关
    public static int pageSize=7;//页面大小
    public static int totalCount=0;//数据总数
    public static int currentPage=1;//当前页码
    public static int pageCount=1;//总页数

    public static String isSchool="1";//招聘信息类别 1 应届生 0 非应届生

    public static String updateViewsStatus="初始状态";//"更新中..."  "更新完成"

    //去重
    public static Set<String> set= Collections.synchronizedSet(new HashSet<>());

    //统计已完成更新线程数
    public static int finishUpdateThreadCount=0;

    public static void initGlobelData(){
        status=MyConstants.status0;
        startTime=System.currentTimeMillis();
        endTime=0;
        duration="";

        dataCount=0;
        crawlerSpeed="";
        requestTimes=0;

        realThreadCount=0;
        lastThreadCount=0;
        compareTimes=0;

        unuseRequestCount=0;
        unuseTaskCount=0;
        executedTaskCount=0;

    }

    //时间转换
    public static String timeTransfer(long time){
        SimpleDateFormat formatter = new SimpleDateFormat(MyConstants.dateFormatString);
        Date date = new Date(time);
        return formatter.format(date);
    }

    //计算时长
    public static String caculateDuration(long start,long end){
        String res="";
        if(start<end){
            long temp=end - start;
            long ms=temp%1000;  //毫秒

            long second=temp/1000;  //总秒数
            long minute=second/60;  //总分钟数
            second=second%60;       //秒数

            long hour=minute/60;   //总小时数
            minute=minute%60;      //分钟数

/*            if(ms!=0){
                res=ms+"毫秒"+res;
            }*/

            if(second!=0){
                res=second+"秒"+res;
            }
            if(minute!=0){
                res=minute+"分"+res;
            }
            if(hour!=0){
                res=hour+"时"+res;
            }
        }
        return res;
    }
}
