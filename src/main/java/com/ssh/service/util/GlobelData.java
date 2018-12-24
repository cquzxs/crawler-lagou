package com.ssh.service.util;

import com.ssh.model.constants.MyConstants;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class GlobelData {
    //分页相关
    public static int pageSize=7;//页面大小
    public static int totalCount=0;//数据总数
    public static int currentPage=1;//当前页码
    public static int pageCount=1;//总页数

    //去重
    public static Set<String> set= Collections.synchronizedSet(new HashSet<>());

    //时间转换
    public static String timeTransfer(long time){
        SimpleDateFormat formatter = new SimpleDateFormat(MyConstants.dateFormatString);
        Date date = new Date(time);
        return formatter.format(date);
    }

}
