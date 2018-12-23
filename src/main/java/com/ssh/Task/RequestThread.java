package com.ssh.Task;

import com.ssh.constants.MyConstants;
import com.ssh.entity.RecruitmentInfo;
import com.ssh.model.*;
import com.ssh.model.api.AbstractModel;
import com.ssh.service.api.IRecruitmentInfoSevice;
import com.ssh.util.GlobelData;
import com.ssh.util.RequestUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

/**
 * 用于请求职位列表页面
 */
public class RequestThread implements Runnable{
    private IRecruitmentInfoSevice recruitmentInfoService;
    private boolean isFirst;//是否是第一页
    private String jobCategory="";//岗位类别
    private RequestParams requestParams;//请求所需参数
    private ExecutorService executorService;//线程池
    private String threadIdentify="";//线程标识

    public static int listSize=1;//用于判断当前请求页的上一页的数据量是否为0

    public RequestThread(RequestParams requestParams, IRecruitmentInfoSevice recruitmentInfoService, boolean isFirst, String jobCategory,ExecutorService executorService,String threadIdentify){
        this.requestParams=requestParams;
        this.recruitmentInfoService=recruitmentInfoService;
        this.isFirst=isFirst;
        this.jobCategory=jobCategory;
        this.executorService=executorService;
        this.threadIdentify=threadIdentify;
    }

    /**
     * 执行请求任务
     */
    @Override
    public void run() {
        try{
            //System.out.println("当前线程名称："+Thread.currentThread().getName());
            if(this.isFirst){   //如果是第一页，则将listSize初始化为1
                listSize=1;
            }
            if(listSize==0 && this.threadIdentify.indexOf(GlobelData.currentFirstRequestThread)>=0){ //当前线程是请求第一页线程产生的请求其他页线程
                GlobelData.unuseTaskCount++;//无效任务数加1，因为listSize=0，说明开始无效请求了，这个时候不让它请求，减少无效请求数
            }else{
                runHelp();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void runHelp() throws Exception{
        //执行请求
        ResourceBundle resourceBundle = ResourceBundle.getBundle("crawlerSetting");
        AbstractModel abstractModel=RequestUtil.requestUri(requestParams);
        //请求数加1
        GlobelData.requestTimes++;
        //解析数据
        List<RecruitmentInfo> oldList=parseData(abstractModel);//去重后listsize=0和没去重listsize=0是不一样的
        List<RecruitmentInfo> list=new ArrayList<>();//去重后listsize=0未开始开始无效请求，没去重listsize=0开始了无效请求
        //存储数据
        listSize=oldList.size();
        for (RecruitmentInfo r:oldList) {
            if(!MyConstants.one.equals(r.getDetailUrl())){
                list.add(r);
            }
        }
        if(list.size()>0){
            GlobelData.dataCount=GlobelData.dataCount+list.size();
            saveData(list);
        }
        if(listSize==0){
            String[] temp=this.threadIdentify.split("-");//listSize=0，设置当前第一页标识
            GlobelData.currentFirstRequestThread=temp[0]+"-"+temp[1];
            GlobelData.unuseRequestCount++;//无效请求数加1，因为请求的数据量为0
        }
        //请求其他页的页面
        if(this.isFirst){
            requestOtherPage(abstractModel);
        }
    }

    //解析数据
    public List<RecruitmentInfo> parseData(AbstractModel model){
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
            if("1".equals(GlobelData.isSchool)){  //是否是应届生
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
            StringBuffer temp=new StringBuffer(requestParams.getChildUrlHead());
            temp.append(i).append(requestParams.getChildUrlTail());
            //requestParams除了目标地址不同其他都相同，使用浅克隆的方式
            RequestParams  rp = (RequestParams)requestParams.clone();
            rp.setTarget(temp.toString());
            //启动请求其他页的线程
            String threadId=threadIdentify+"-otherPageRequestThread-"+i;
            RequestThread otherPageRequestThread=new RequestThread(rp,this.recruitmentInfoService,false,this.jobCategory,executorService,threadId);
            executorService.execute(otherPageRequestThread);
            //统计线程池中的线程数
            GlobelData.realThreadCount++;
        }
    }
    //存储数据
    public boolean saveData(List<RecruitmentInfo> list){
        if(this.recruitmentInfoService!=null){
            return this.recruitmentInfoService.addRecruitmentInfoList(list);
        }else{
            System.out.println("recruitmentInfoService null");
            return false;
        }
    }
}
