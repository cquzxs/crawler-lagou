package com.ssh.service.crawler.impl;

import com.ssh.Task.ManagementThread;
import com.ssh.Task.MonitorThread;
import com.ssh.constants.MyConstants;
import com.ssh.controller.CrawlerController;
import com.ssh.service.api.IRecruitmentInfoSevice;
import com.ssh.service.crawler.api.ICrawlerInfoService;
import com.ssh.util.GetUrls;
import com.ssh.util.GlobelData;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 爬取拉勾网招聘信息
 */
@Service("crawlerInfoService")
public class CrawlerInfoServiceImpl implements ICrawlerInfoService{
    private static final Logger logger = Logger.getLogger(CrawlerInfoServiceImpl.class);

    @Resource(name="recruitmentInfoService")
    private IRecruitmentInfoSevice recruitmentInfoService;//招聘信息表

    private boolean isRunning = false;
    private String isSchool="1";
    private String isDelete="1";

    private HashMap<String,String> map=new HashMap<>();//用于存储职位类别，key为职位名，value为url编码
    private ExecutorService executorService;//线程池
    private static int lastRequestTimes=0;//上一次的请求次数，用于计算请求速度

    /**
     * 开始爬取
     *
     * @param isSchool 是否爬取应届生招聘信息
     * @param isDelete 是否删除数据库数据
     * @throws Exception 异常
     */
    @Override
    public void startCrawler(String isSchool, String isDelete) throws Exception{
        if(!isRunning){
            logger.info("开始爬取招聘信息");
            init();
            deleteData(isDelete);
            requestFirstPage();
            startCrawler();
        }
    }

    /**
     * 开启爬虫任务
     */
    private void startCrawler() {
        //启动爬虫管理线程
        if(executorService != null){
            executorService = Executors.newFixedThreadPool(MyConstants.poolSize);
        }
        Iterator iterator=map.entrySet().iterator();
        ManagementThread managementThread=new ManagementThread(iterator,recruitmentInfoService,executorService);
        executorService.execute(managementThread);
        logger.info("启动爬虫管理线程");

        //启动监听者线程
        MonitorThread monitorThread=new MonitorThread(executorService);
        executorService.execute(monitorThread);
        logger.info("启动监听者线程");
    }

    /**
     * 请求拉勾网首页，获取所有职位类别
     */
    private void requestFirstPage() throws Exception{
        logger.info("请求拉勾网首页，获取所有职位类别");
        map=GetUrls.getUrls();
        System.out.println("职位类别总数："+map.size());
    }

    /**
     * 删除数据库数据
     *
     * @param isDelete 是否删除数据库数据
     */
    private void deleteData(String isDelete) {
        if(MyConstants.one.equals(isDelete)){    //选择了删除现有数据
            logger.info("删除所有数据");
            System.out.println("delete...");
            recruitmentInfoService.deleteAllRecruitmentInfo();
            System.out.println("delete finish.");
        }
    }

    /**
     * 爬虫初始化
     */
    private void init() {
        logger.info("初始化全局数据");
        GlobelData.set.clear();
        GlobelData.initGlobelData();
        GlobelData.status= MyConstants.status4;
        lastRequestTimes=0;
        GlobelData.isSchool=isSchool;
    }

    /**
     * 停止爬取
     */
    @Override
    public void stopCrawler() {

    }
}