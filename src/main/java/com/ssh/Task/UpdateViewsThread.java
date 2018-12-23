package com.ssh.Task;

import com.ssh.constants.MyConstants;
import com.ssh.service.api.*;
import com.ssh.transfer.*;
import com.ssh.util.GlobelData;

import java.util.concurrent.CountDownLatch;


/**
 * Project Name:SSH
 * File Name:UpdateViewsThread
 * Package Name:com.ssh.Task
 * Date:2018/5/23 16:04
 * Author:zengxueshan
 * Description:
 * Copyright (c) 2018, 重庆云凯科技有限公司 All Rights Reserved.
 */
public class UpdateViewsThread implements Runnable{

    private IRecruitmentInfoSevice recruitmentInfoService;//招聘信息表

    private ISalaryCompareViewService salaryCompareViewService;//薪资对比视图

    private ICityDistributeViewService cityDistributeViewService; //城市分布视图

    private IBaseCreateTimeViewService baseCreateTimeViewService;//视图3

    private IBaseCompanySizeViewService baseCompanySizeViewService;//视图4

    private IBaseSalaryRangeViewService baseSalaryRangeViewService;//视图5

    public UpdateViewsThread(IRecruitmentInfoSevice recruitmentInfoService, ISalaryCompareViewService salaryCompareViewService, ICityDistributeViewService cityDistributeViewService, IBaseCreateTimeViewService baseCreateTimeViewService, IBaseCompanySizeViewService baseCompanySizeViewService, IBaseSalaryRangeViewService baseSalaryRangeViewService) {
        this.recruitmentInfoService = recruitmentInfoService;
        this.salaryCompareViewService = salaryCompareViewService;
        this.cityDistributeViewService = cityDistributeViewService;
        this.baseCreateTimeViewService = baseCreateTimeViewService;
        this.baseCompanySizeViewService = baseCompanySizeViewService;
        this.baseSalaryRangeViewService = baseSalaryRangeViewService;
    }
    @Override
    public void run() {
        //设置更新状态为更新中
        GlobelData.updateViewsStatus= MyConstants.updateViewsStatus0;

        //将数据转换为薪资对比视图
        GlobelData.finishUpdateThreadCount=0;
        DataToView1 dataToView1=new DataToView1(recruitmentInfoService,salaryCompareViewService);
        new Thread(dataToView1).start();

        //将数据转换为城市分布视图
        DataToView2 dataToView2=new DataToView2(recruitmentInfoService,cityDistributeViewService);
        new Thread(dataToView2).start();

        //将数据转换为视图3
        DataToView3 dataToView3=new DataToView3(recruitmentInfoService,baseCreateTimeViewService);
        new Thread(dataToView3).start();

        //将数据转换为视图4
        DataToView4 dataToView4=new DataToView4(recruitmentInfoService,baseCompanySizeViewService);
        new Thread(dataToView4).start();

        //将数据转换为视图5
        DataToView5 dataToView5=new DataToView5(recruitmentInfoService,baseSalaryRangeViewService);
        new Thread(dataToView5).start();

/*        CountDownLatch count=new CountDownLatch(5);
        try {
            count.await();//await后没有事情做，就不用CountDownLatch了
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
