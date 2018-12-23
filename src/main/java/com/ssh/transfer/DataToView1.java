package com.ssh.transfer;

import com.ssh.controller.CrawlerController;
import com.ssh.entity.RecruitmentInfo;
import com.ssh.entity.SalaryCompareView;
import com.ssh.service.api.IRecruitmentInfoSevice;
import com.ssh.service.api.ISalaryCompareViewService;
import com.ssh.util.GlobelData;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Name:SSH
 * File Name:DataToView1
 * Package Name:com.ssh.transfer
 * Date:2018/5/28 16:53
 * Author:zengxueshan
 * Description:
 * Copyright (c) 2018, 重庆云凯科技有限公司 All Rights Reserved.
 */
public class DataToView1 implements Runnable{
    private IRecruitmentInfoSevice recruitmentInfoService;

    private ISalaryCompareViewService salaryCompareViewService;//视图1

    private static final Logger logger = Logger.getLogger(DataToView1.class);

    public DataToView1(IRecruitmentInfoSevice recruitmentInfoService,ISalaryCompareViewService salaryCompareViewService){
        this.recruitmentInfoService=recruitmentInfoService;
        this.salaryCompareViewService=salaryCompareViewService;
    }

    @Override
    public void run() {
        dataToSalaryCompareView();
        GlobelData.finishUpdateThreadCount++;
    }

    /**
     * 视图1：薪资对比视图
     * 岗位，工作城市，平均薪资
     */
    public void dataToSalaryCompareView(){
        logger.info("视图1 start--------------------");
        System.out.println("查询所有招聘信息中...");
        List<RecruitmentInfo> list=this.recruitmentInfoService.selectAllRecruitmentInfo();//初始数据
        System.out.println("查询所有招聘信息完成.");

        System.out.println("删除原有数据中...");
        this.salaryCompareViewService.deleteAll();//删除中间数据
        System.out.println("删除原有数据完成.");

        List<SalaryCompareView> viewList=new ArrayList<>();//中间数据
        for (int i = 0; i < list.size(); i++) {
            RecruitmentInfo recruitmentInfo = list.get(i);
            SalaryCompareView salaryCompareView = new SalaryCompareView();
            salaryCompareView.setJobName(recruitmentInfo.getJobName());
            salaryCompareView.setCityName(recruitmentInfo.getCityName());
            salaryCompareView.setAvgSarary(avgSalary(recruitmentInfo.getSalaryRange()));
            salaryCompareView.setIsSchool(recruitmentInfo.getFollowsCount());
            viewList.add(salaryCompareView);
        }
        System.out.println("保存中间数据中...");
        this.salaryCompareViewService.addList(viewList);
        System.out.println("保存中间数据完成.");

        System.out.println("获取最终数据中...");
        List<SalaryCompareView> viewList2=this.salaryCompareViewService.selectAvgSalary();//最终数据
        System.out.println("获取最终数据完成.");

        System.out.println("删除中间数据中...");
        this.salaryCompareViewService.deleteAll();
        System.out.println("删除中间数据完成.");

        System.out.println("保存最终数据中...");
        this.salaryCompareViewService.addList(viewList2);
        System.out.println("保存最终数据完成.");

        logger.info("视图1 end--------------------");
    }

    /**
     * 可能情况有
     * 10k-20k
     * 100k+
     * 100k以上
     * 100k-100k以上
     */
    private int avgSalary(String salaryRange){
        String[] str=salaryRange.split("-");
        int res=0;
        if(str.length>=2){
            if(salaryRange.indexOf("以上")>=0){//针对100k+以上的特殊情况
                res=100;
            }else{
                str[0]=str[0].substring(0,str[0].length()-1);
                str[1]=str[1].substring(0,str[1].length()-1);
                int[] temp=new int[2];
                temp[0]=Integer.parseInt(str[0]);
                temp[1]=Integer.parseInt(str[1]);
                res=(temp[0]+temp[1])/2;
            }
        }else{
            if(salaryRange.indexOf("+")>=0){
                res=Integer.parseInt( salaryRange.substring(0,salaryRange.length()-2) );
            }
            if(salaryRange.indexOf("以上")>=0){
                res=Integer.parseInt( salaryRange.substring(0,salaryRange.length()-3) );
            }
        }
        return res;
    }
}
