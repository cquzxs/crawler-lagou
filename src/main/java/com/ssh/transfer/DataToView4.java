package com.ssh.transfer;

import com.ssh.entity.BaseCompanySizeView;
import com.ssh.service.api.IBaseCompanySizeViewService;
import com.ssh.service.api.IRecruitmentInfoSevice;
import com.ssh.util.GlobelData;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Project Name:SSH
 * File Name:DataToView4
 * Package Name:com.ssh.transfer
 * Date:2018/5/28 16:53
 * Author:zengxueshan
 * Description:
 * Copyright (c) 2018, 重庆云凯科技有限公司 All Rights Reserved.
 */
public class DataToView4 implements Runnable{
    private IRecruitmentInfoSevice recruitmentInfoService;

    private IBaseCompanySizeViewService baseCompanySizeViewService;//视图4
    private static final Logger logger = Logger.getLogger(DataToView4.class);
    public DataToView4(IRecruitmentInfoSevice recruitmentInfoService, IBaseCompanySizeViewService baseCompanySizeViewService) {
        this.recruitmentInfoService = recruitmentInfoService;
        this.baseCompanySizeViewService = baseCompanySizeViewService;
    }
    @Override
    public void run() {
        dataToBaseCompanySizeView();
        GlobelData.finishUpdateThreadCount++;
    }
    /**
     * 视图4：基于公司规模的视图
     */
    public void dataToBaseCompanySizeView(){
        logger.info("视图4 start---------------------");
        System.out.println("查询所有招聘信息中...");
        List<BaseCompanySizeView> list=this.recruitmentInfoService.selectBaseCompanySizeViewCount();//初始数据
        System.out.println("查询所有招聘信息完成.");

        System.out.println("删除原有数据中...");
        this.baseCompanySizeViewService.deleteAll();//删除原有数据
        System.out.println("删除原有数据完成.");

        System.out.println("保存最终数据中...");
        this.baseCompanySizeViewService.addList(list);//保存中间数据
        System.out.println("保存最终数据完成.");

        logger.info("视图4 end--------------------");
    }
}
