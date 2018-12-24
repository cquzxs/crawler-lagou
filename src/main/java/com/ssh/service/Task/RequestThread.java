package com.ssh.service.Task;

import com.ssh.model.constants.MyConstants;
import com.ssh.model.entity.RecruitmentInfo;
import com.ssh.model.RequestParams;
import com.ssh.model.crawler.api.AbstractModel;
import com.ssh.service.basic.api.IRecruitmentInfoService;
import com.ssh.service.crawler.impl.CrawlerInfoServiceImpl;
import com.ssh.service.util.GlobelData;
import com.ssh.service.util.RequestUtil;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 用于请求职位列表页面
 */
public class RequestThread implements Runnable{
    private static final Logger logger = Logger.getLogger(RequestThread.class);
    private IRecruitmentInfoService recruitmentInfoService;
    private boolean isFirst;//是否是第一页
    private String jobCategory="";//岗位类别
    private RequestParams requestParams;//请求所需参数
    private ExecutorService executorService;//线程池
    private String threadIdentify="";//线程标识
    private String isSchool = "1";


    public RequestThread(RequestParams requestParams
            , IRecruitmentInfoService recruitmentInfoService
            , boolean isFirst
            , String jobCategory
            , ExecutorService executorService
            , String threadIdentify
            , String isSchool){
        this.requestParams=requestParams;
        this.recruitmentInfoService=recruitmentInfoService;
        this.isFirst=isFirst;
        this.jobCategory=jobCategory;
        this.executorService=executorService;
        this.threadIdentify=threadIdentify;
        this.isSchool = isSchool;
    }

    /**
     * 执行请求任务
     */
    @Override
    public void run() {
        try{
            if(!CrawlerInfoServiceImpl.isRunning){
                return;
            }
            Thread.sleep(1000);
            AbstractModel abstractModel = requestPage();
            //请求其他页的页面
            if(this.isFirst){
                requestOtherPage(abstractModel);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * 请求页面
     * @throws Exception 异常
     */
    private AbstractModel requestPage() throws Exception{
        //执行请求
        AbstractModel abstractModel=RequestUtil.requestUri(requestParams);
        //解析数据
        List<RecruitmentInfo> oldList=parseData(abstractModel);
        List<RecruitmentInfo> list=new ArrayList<>();
        //存储数据
        for (RecruitmentInfo r:oldList) {
            if(!MyConstants.one.equals(r.getDetailUrl())){
                list.add(r);
            }
        }
        if(list.size()>0){
            saveData(list);
        }
        return abstractModel;
    }

    //解析数据
    private List<RecruitmentInfo> parseData(AbstractModel model){
        List<RecruitmentInfo> list=new ArrayList<>();
        JSONObject object=(JSONObject)model.getSourceEntity();
        //获取当前页面json数据
        JSONArray result=(JSONArray)((JSONObject)((JSONObject)object.get("content")).get("positionResult")).get("result");
        for (int i = 0; i < result.length(); i++) {
            RecruitmentInfo recruitmentInfo=new RecruitmentInfo();
            JSONObject obj1=(JSONObject)result.get(i);
            recruitmentInfo.setCompanyName(obj1.get("companyFullName").toString());
            recruitmentInfo.setJobName(jobCategory);
            recruitmentInfo.setCityName(obj1.get("city").toString());
            recruitmentInfo.setSalaryRange(obj1.get("salary").toString());
            if("1".equals(this.isSchool)){  //是否是应届生
                recruitmentInfo.setFollowsCount(1);
            }else{
                recruitmentInfo.setFollowsCount(0);
            }
            recruitmentInfo.setDetailUrl("https://www.lagou.com/jobs/"+obj1.get("positionId").toString()+".html");
            recruitmentInfo.setCreateTime(obj1.get("createTime").toString());

            recruitmentInfo.setCompanySize(obj1.get("companySize").toString());
            recruitmentInfo.setPositionAdvantage(obj1.get("positionAdvantage").toString());

            if(!GlobelData.set.contains(recruitmentInfo.getDetailUrl())){ //去重
                GlobelData.set.add(recruitmentInfo.getDetailUrl());
            }else{
                recruitmentInfo.setDetailUrl(MyConstants.one);//用作判断，无实际意义
            }
            list.add(recruitmentInfo);

        }
        return list;
    }
    //请求其他页的页面
    private void requestOtherPage(AbstractModel model) throws CloneNotSupportedException {
        JSONObject object=(JSONObject)model.getSourceEntity();
        //获取总页数,用以请求其他页的页面
        int pageSize=Integer.parseInt( ((JSONObject)object.get("content")).get("pageSize").toString() );
        int totalCount=Integer.parseInt( ((JSONObject)((JSONObject)object.get("content")).get("positionResult")).get("totalCount").toString() );
        int pageCount=totalCount/pageSize+1;
        for (int i = 2; i <= pageCount; i++) {        //从第二页开始
            //拼接其他页的地址
            String temp = requestParams.getChildUrlHead();
            temp = temp + requestParams.getChildUrlTail();
            //requestParams除了目标地址不同其他都相同，使用浅克隆的方式
            RequestParams  rp = (RequestParams)requestParams.clone();
            rp.setTarget(temp);
            //启动请求其他页的线程
            String threadId=threadIdentify+"-otherPageRequestThread-"+i;
            RequestThread otherPageRequestThread=new RequestThread(rp,this.recruitmentInfoService,false,this.jobCategory,executorService,threadId,this.isSchool);
            executorService.execute(otherPageRequestThread);
        }
    }
    //存储数据
    private boolean saveData(List<RecruitmentInfo> list){
        if(this.recruitmentInfoService!=null){
            return this.recruitmentInfoService.addRecruitmentInfoList(list);
        }else{
            logger.info("recruitmentInfoService null");
            return false;
        }
    }
}
