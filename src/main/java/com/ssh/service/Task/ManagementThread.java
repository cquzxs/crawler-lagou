package com.ssh.service.Task;

import com.ssh.model.constants.MyConstants;
import com.ssh.model.RequestParams;
import com.ssh.service.basic.api.IRecruitmentInfoService;

import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

/**
 * 用于启动爬虫的爬虫管理线程
 */
public class ManagementThread implements Runnable{
    private IRecruitmentInfoService recruitmentInfoService;
    private Iterator iterator;
    private ExecutorService executorService;//线程池
    private String isSchool="1";

    public ManagementThread(Iterator iterator, IRecruitmentInfoService recruitmentInfoService, ExecutorService executorService, String isSchool){
        this.iterator=iterator;
        this.recruitmentInfoService=recruitmentInfoService;
        this.executorService=executorService;
        this.isSchool = isSchool;
    }

    @Override
    public void run() {
        ResourceBundle resourceBundle;//用于读取配置文件
        int k=0;//用于标识线程
        if(MyConstants.one.equals(this.isSchool)){ //是否是应届生
            resourceBundle = ResourceBundle.getBundle("fresh");
        }else{
            resourceBundle = ResourceBundle.getBundle("non-fresh");
        }
        while(iterator.hasNext()){
            Map.Entry entry=(Map.Entry)iterator.next();
            //拼接请求的目标地址
            String target=resourceBundle.getString("TARGET_HEAD")+entry.getValue()+resourceBundle.getString("TARGET_TAIL");
            //拼接请求的引用地址
            String referer=resourceBundle.getString("REFERER_HEAD")+entry.getValue()+resourceBundle.getString("REFERER_TAIL");
            //用于拼接分页地址
            String child_url_head=resourceBundle.getString("CHILD_URL_HEAD");
            String child_url_tail=resourceBundle.getString("CHILD_URL_TAIL")+entry.getValue();
            RequestParams requestParams=new RequestParams(
                    target,
                    resourceBundle.getString("COOKIE"),
                    referer,
                    resourceBundle.getString("CHARSET"),
                    resourceBundle.getString("USER_AGENT"),
                    child_url_head,
                    child_url_tail
            );

            //开始爬取该职位的第一页
            String threadIdentify="firstPageRequestThread-"+k;
            RequestThread firstPageRequestThread=new RequestThread(requestParams,recruitmentInfoService,true,entry.getKey().toString(),executorService,threadIdentify,isSchool);
            //将该线程加入到线程池执行
            executorService.execute(firstPageRequestThread);
            k++;
        }
    }
}
