package com.ssh.service.crawler.impl;

import com.ssh.service.Task.ManagementThread;
import com.ssh.model.constants.MyConstants;
import com.ssh.service.basic.api.IRecruitmentInfoService;
import com.ssh.service.crawler.api.ICrawlerInfoService;
import com.ssh.service.util.GetUrls;
import com.ssh.service.util.GlobelData;
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
    private IRecruitmentInfoService recruitmentInfoService;//招聘信息表

    public static boolean isRunning = false;
    private HashMap<String,String> map=new HashMap<>();//用于存储职位类别，key为职位名，value为url编码
    private ExecutorService executorService;//线程池

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
            startCrawler(isSchool);
        }
    }

    /**
     * 开启爬虫任务
     *
     * @param isSchool 是否是应届生
     */
    private void startCrawler(String isSchool) {
        //启动爬虫管理线程
        if(executorService != null){
            executorService = Executors.newFixedThreadPool(MyConstants.poolSize);
        }
        Iterator iterator=map.entrySet().iterator();
        ManagementThread managementThread=new ManagementThread(iterator,recruitmentInfoService,executorService,isSchool);
        executorService.execute(managementThread);
        logger.info("启动爬虫管理线程");
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
    }

    /**
     * 停止爬取
     */
    @Override
    public void stopCrawler() {
        isRunning = false;
    }
}