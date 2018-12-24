package com.ssh.service.views.impl;

import com.ssh.service.basic.api.*;
import com.ssh.service.views.api.IViewsService;
import com.ssh.service.Task.transfer.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zxs on 2018/12/24.
 * 更新视图信息
 */
@Service("viewsService")
public class ViewsServiceImpl implements IViewsService{

    @Resource(name="recruitmentInfoService")
    private IRecruitmentInfoService recruitmentInfoService;//招聘信息表

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

    public static boolean isRunning = false;

    /**
     * 更新视图信息
     */
    @Override
    public void updateViews() {
        if(!isRunning){
            //将数据转换为薪资对比视图
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

            isRunning = true;
        }
    }
}