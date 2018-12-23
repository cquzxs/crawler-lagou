package com.ssh.transfer;

import com.ssh.entity.CityDistributeView;
import com.ssh.entity.RecruitmentInfo;
import com.ssh.service.api.ICityDistributeViewService;
import com.ssh.service.api.IRecruitmentInfoSevice;
import com.ssh.util.GlobelData;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Name:SSH
 * File Name:DataToView2
 * Package Name:com.ssh.transfer
 * Date:2018/5/28 16:53
 * Author:zengxueshan
 * Description:
 * Copyright (c) 2018, 重庆云凯科技有限公司 All Rights Reserved.
 */
public class DataToView2 implements Runnable{
    private IRecruitmentInfoSevice recruitmentInfoService;

    private ICityDistributeViewService cityDistributeViewService;//视图2
    private static final Logger logger = Logger.getLogger(DataToView2.class);
    public DataToView2(IRecruitmentInfoSevice recruitmentInfoService, ICityDistributeViewService cityDistributeViewService) {
        this.recruitmentInfoService = recruitmentInfoService;
        this.cityDistributeViewService = cityDistributeViewService;
    }

    @Override
    public void run() {
        dataToCityDistributeView();
        GlobelData.finishUpdateThreadCount++;
    }

    /**
     * 视图2：城市分布视图
     * 岗位，工作城市，个数，占比
     */
    public void dataToCityDistributeView(){
        logger.info("视图2 start--------------------");
        System.out.println("查询所有招聘信息中...");
        List<RecruitmentInfo> list=this.recruitmentInfoService.selectAllRecruitmentInfo();//初始数据
        System.out.println("查询所有招聘信息完成.");

        System.out.println("删除原有数据中...");
        this.cityDistributeViewService.deleteAll();//删除中间数据
        System.out.println("删除原有数据完成.");

        List<CityDistributeView> viewList=new ArrayList<>();//中间数据
        for (int i = 0; i < list.size(); i++) {
            RecruitmentInfo recruitmentInfo = list.get(i);
            CityDistributeView cityDistributeView = new CityDistributeView();
            cityDistributeView.setCityName(recruitmentInfo.getCityName());
            cityDistributeView.setJobName(recruitmentInfo.getJobName());
            cityDistributeView.setCount(1);
            cityDistributeView.setRatio(0.0);
            cityDistributeView.setIsSchool(recruitmentInfo.getFollowsCount());
            viewList.add(cityDistributeView);
        }
        System.out.println("保存中间数据中...");
        this.cityDistributeViewService.addList(viewList);
        System.out.println("保存中间数据完成.");

        System.out.println("获取最终数据中...");
        List<CityDistributeView> viewList2=this.cityDistributeViewService.selectCount();//最终数据
        System.out.println("获取最终数据完成.");

        System.out.println("删除中间数据中...");
        this.cityDistributeViewService.deleteAll();
        System.out.println("删除中间数据完成.");

        System.out.println("保存最终数据中...");
        this.cityDistributeViewService.addList(viewList2);
        System.out.println("保存最终数据完成.");

        logger.info("视图2 end--------------------");
    }
}
