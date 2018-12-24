package com.ssh.service.Task.transfer;

import com.ssh.model.entity.CityDistributeView;
import com.ssh.model.entity.RecruitmentInfo;
import com.ssh.service.basic.api.ICityDistributeViewService;
import com.ssh.service.basic.api.IRecruitmentInfoService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Name:SSH
 * File Name:DataToView2
 * Package Name:com.ssh.service.Task.transfer
 * Date:2018/5/28 16:53
 * Author:zengxueshan
 * Description:
 * Copyright (c) 2018, 重庆云凯科技有限公司 All Rights Reserved.
 */
public class DataToView2 implements Runnable{
    private IRecruitmentInfoService recruitmentInfoService;

    private ICityDistributeViewService cityDistributeViewService;//视图2
    private static final Logger logger = Logger.getLogger(DataToView2.class);
    public DataToView2(IRecruitmentInfoService recruitmentInfoService, ICityDistributeViewService cityDistributeViewService) {
        this.recruitmentInfoService = recruitmentInfoService;
        this.cityDistributeViewService = cityDistributeViewService;
    }

    @Override
    public void run() {
        dataToCityDistributeView();
    }

    /**
     * 视图2：城市分布视图
     * 岗位，工作城市，个数，占比
     */
    public void dataToCityDistributeView(){
        logger.info("视图2 start--------------------");
        List<RecruitmentInfo> list=this.recruitmentInfoService.selectAllRecruitmentInfo();//初始数据

        this.cityDistributeViewService.deleteAll();//删除中间数据

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
        this.cityDistributeViewService.addList(viewList);

        List<CityDistributeView> viewList2=this.cityDistributeViewService.selectCount();//最终数据

        this.cityDistributeViewService.deleteAll();

        this.cityDistributeViewService.addList(viewList2);

        logger.info("视图2 end--------------------");
    }
}
