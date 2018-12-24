package com.ssh.service.Task.transfer;

import com.ssh.entity.BaseCreateTimeView;
import com.ssh.entity.RecruitmentInfo;
import com.ssh.service.basic.api.IBaseCreateTimeViewService;
import com.ssh.service.basic.api.IRecruitmentInfoService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Name:SSH
 * File Name:DataToView3
 * Package Name:com.ssh.service.Task.transfer
 * Date:2018/5/28 16:53
 * Author:zengxueshan
 * Description:
 * Copyright (c) 2018, 重庆云凯科技有限公司 All Rights Reserved.
 */
public class DataToView3 implements Runnable{
    private IRecruitmentInfoService recruitmentInfoService;

    private IBaseCreateTimeViewService baseCreateTimeViewService;//视图3
    private static final Logger logger = Logger.getLogger(DataToView3.class);
    public DataToView3(IRecruitmentInfoService recruitmentInfoService, IBaseCreateTimeViewService baseCreateTimeViewService) {
        this.recruitmentInfoService = recruitmentInfoService;
        this.baseCreateTimeViewService = baseCreateTimeViewService;
    }

    @Override
    public void run() {
        dataToBaseCreateTimeView();
    }
    /**
     * 视图3：基于发布时间的视图
     */
    public void dataToBaseCreateTimeView(){
        logger.info("视图3 start---------------------");
        System.out.println("查询所有招聘信息中...");
        List<RecruitmentInfo> list=this.recruitmentInfoService.selectAllRecruitmentInfo();//初始数据
        System.out.println("查询所有招聘信息完成.");

        System.out.println("删除原有数据中...");
        this.baseCreateTimeViewService.deleteAll();//删除中间数据
        System.out.println("删除原有数据完成.");

        List<BaseCreateTimeView> viewList=new ArrayList<>();
        for (RecruitmentInfo s:list) {
            BaseCreateTimeView baseCreateTimeView=new BaseCreateTimeView();
            baseCreateTimeView.setJobName(s.getJobName());
            baseCreateTimeView.setCreateTime(s.getCreateTime().substring(0,10));
            baseCreateTimeView.setCount(0);
            baseCreateTimeView.setIsSchool(s.getFollowsCount());
            viewList.add(baseCreateTimeView);
        }
        System.out.println("保存中间数据中...");
        this.baseCreateTimeViewService.addList(viewList);//保存中间数据
        System.out.println("保存中间数据完成.");

        System.out.println("获取最终数据中...");
        List<BaseCreateTimeView> viewList2=this.baseCreateTimeViewService.selectCount();//获取最终数据
        System.out.println("获取最终数据中...");

        System.out.println("删除中间数据中...");
        this.baseCreateTimeViewService.deleteAll();//删除中间数据
        System.out.println("删除中间数据完成.");

        System.out.println("保存最终数据中...");
        this.baseCreateTimeViewService.addList(viewList2);//保存最终数据
        System.out.println("保存最终数据完成.");

        logger.info("视图3 end--------------------");
    }

}