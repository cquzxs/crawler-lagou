package com.ssh.controller;

import com.alibaba.fastjson.JSON;
import com.ssh.Task.UpdateViewsThread;
import com.ssh.constants.MyConstants;
import com.ssh.Task.MonitorThread;
import com.ssh.service.api.*;
import com.ssh.service.crawler.api.ICrawlerInfoService;
import com.ssh.util.GetUrls;
import com.ssh.Task.ManagementThread;
import com.ssh.model.CrawlerInfo;
import com.ssh.util.GlobelData;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class CrawlerController {
    @Resource(name="crawlerInfoService")
    private ICrawlerInfoService crawlerInfoService;//爬虫服务

    @Resource(name="recruitmentInfoService")
    private IRecruitmentInfoSevice recruitmentInfoService;//招聘信息表
    @Resource(name="salaryCompareViewService")
    private ISalaryCompareViewService salaryCompareViewService;//薪资对比视图
    @Resource(name="cityDistributeViewService")
    private ICityDistributeViewService cityDistributeViewService; //城市分布视图
    @Resource(name="baseCreateTimeViewService")
    private IBaseCreateTimeViewService baseCreateTimeViewService;//视图3
    @Resource(name="baseCompanySizeViewService")
    private IBaseCompanySizeViewService baseCompanySizeViewService;//视图4
    @Resource(name="baseSalaryRangeViewService")
    private IBaseSalaryRangeViewService baseSalaryRangeViewService;//视图5

    private static final Logger logger = Logger.getLogger(CrawlerController.class);

    private ExecutorService executorService;//线程池
    private static int lastRequestTimes=0;//上一次的请求次数，用于计算请求速度

    private HashMap<String,String> map=new HashMap<>();//用于存储职位类别，key为职位名，value为url编码
    private String isSchool="1";
    private String isDelete="1";

    @RequestMapping(value="/startCrawler",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String startCrawler(String isSchool,String isDelete) throws Exception {
        try{
            this.crawlerInfoService.startCrawler(isSchool, isDelete);
        }catch (Exception e){
            logger.info("开始爬取任务失败", e);
        }
        //返回json数据
        String res=getCrawlerInfo2();
        return res;
    }

    @RequestMapping("/getCrawlerPage")
    public String getCrawlerPage(){
        logger.info("爬虫管理页");
        return "CrawlerPage";
    }


    @RequestMapping(value="/getCrawlerInfo",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String getCrawlerInfo2(){     //返回json数据
        logger.info("获取爬虫对象信息");
        CrawlerInfo res=getCrawlerInfo();
        String resu=JSON.toJSONString(res);
        return resu;
    }

    public CrawlerInfo getCrawlerInfo(){     //返回CrawlerInfo对象
        if( GlobelData.status.equals(MyConstants.status0) ){//未爬取状态时
            GlobelData.initGlobelData();
        }
        CrawlerInfo crawlerInfo=new CrawlerInfo();
        //设置爬取状态
        crawlerInfo.setStatus(GlobelData.status);
        //设置开始时间
        crawlerInfo.setStartTime(GlobelData.timeTransfer(GlobelData.startTime));//开始时间就是点击开始爬取招聘信息按钮的时间
        //设置结束时间
        long endTime=0;
        crawlerInfo.setEndTime(MyConstants.empty);
        if(GlobelData.status.equals(MyConstants.status2) || GlobelData.status.equals(MyConstants.status3)){  //停止爬取或爬取完成时
            endTime=GlobelData.endTime;   //将全局变量中的结束时间设为爬虫的结束时间
            crawlerInfo.setEndTime(GlobelData.timeTransfer(endTime));
        }else{     //若是爬取中，将当前时间记录到全局变量中，但是不将结束时间设为这个，而将结束时间设为空
            endTime = System.currentTimeMillis();    //获取结束时间
            GlobelData.endTime=endTime;
        }
        //设置爬取时长
        String duration=GlobelData.caculateDuration(GlobelData.startTime,endTime);
        crawlerInfo.setDuration(duration);

        //设置已爬取数据总数
        crawlerInfo.setDataCount(GlobelData.dataCount);
        //设置已请求次数
        crawlerInfo.setRequestTimes(GlobelData.requestTimes);
        //设置无效请求次数
        crawlerInfo.setUnuseRequestCount(GlobelData.unuseRequestCount);
        //设置爬取速度
        float speed=(GlobelData.requestTimes-lastRequestTimes)/MyConstants.interval;
        speed=(float)(Math.round(speed*100))/100;    //Math.round向左取整
        lastRequestTimes=GlobelData.requestTimes;
        crawlerInfo.setCrawlerSpeed(speed+MyConstants.speedUnit);

        //设置任务总数
        crawlerInfo.setRequestThreadCount(GlobelData.realThreadCount);
        //设置已执行任务数
        crawlerInfo.setExecutedTaskCount(GlobelData.executedTaskCount);
        //设置无效任务数
        crawlerInfo.setUnuseTaskCount(GlobelData.unuseTaskCount);


        crawlerInfo.setIsSchool(this.isSchool);
        crawlerInfo.setIsDelete(this.isDelete);

        if( GlobelData.status.equals(MyConstants.status0) ){ //未爬取状态时
            crawlerInfo.setStartTime("");
        }
        return crawlerInfo;
    }

    @RequestMapping(value="/stopCrawler",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String stopCrawler(){
        logger.info("停止爬取招聘信息");
        if(executorService!=null){
            executorService.shutdownNow();//停止线程池中的所有线程
        }
        if( GlobelData.status.equals(MyConstants.status1) ) {  //爬取中点击停止爬取招聘信息按钮
            //将爬取状态设为停止爬取
            GlobelData.status=MyConstants.status2;
            //设置停止爬取的时间
            long endTime=System.currentTimeMillis();//将停止爬取的时间记录下来
            GlobelData.endTime=endTime;
        }
        return getCrawlerInfo2();
    }

    @RequestMapping(value="/updateViews",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String updateViews(){     //返回json数据
        logger.info("更新视图信息");
        if(!GlobelData.status.equals(MyConstants.status1)){
            if( "初始状态".equals(GlobelData.updateViewsStatus) || "更新完成".equals(GlobelData.updateViewsStatus)){
                UpdateViewsThread updateViewsThread=new UpdateViewsThread(recruitmentInfoService,
                        salaryCompareViewService,
                        cityDistributeViewService,
                        baseCreateTimeViewService,
                        baseCompanySizeViewService,
                        baseSalaryRangeViewService);
                new Thread(updateViewsThread).start();
            }else{
                logger.info("正在更新，请不要重复点击");
            }
        }else{
            logger.info("正在爬取，请爬取完成后再更新视图信息");
        }
        //返回json数据
        String res=JSON.toJSONString(GlobelData.updateViewsStatus);
        return res;
    }
    @RequestMapping(value="/getUpdateInfo",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String getUpdateInfo(){     //返回json数据
        logger.info("获取更新视图的后台数据");
        if(GlobelData.finishUpdateThreadCount==5){
            //设置更新状态为更新完成
            GlobelData.updateViewsStatus=MyConstants.updateViewsStatus11;
        }else if(GlobelData.finishUpdateThreadCount==0){
           /* //设置更新状态为更新中
            GlobelData.updateViewsStatus= MyConstants.updateViewsStatus0;*/
        }else{
            GlobelData.updateViewsStatus="已更新"+GlobelData.finishUpdateThreadCount+"/5个视图，请稍侯";
        }
        String res=JSON.toJSONString(GlobelData.updateViewsStatus);
        return res;
    }
}
