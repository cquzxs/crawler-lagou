package com.ssh.service.Task.transfer;

import com.ssh.model.entity.BaseCompanySizeView;
import com.ssh.service.basic.api.IBaseCompanySizeViewService;
import com.ssh.service.basic.api.IRecruitmentInfoService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Project Name:SSH
 * File Name:DataToView4
 * Package Name:com.ssh.service.Task.transfer
 * Date:2018/5/28 16:53
 * Author:zengxueshan
 * Description:
 * Copyright (c) 2018, 重庆云凯科技有限公司 All Rights Reserved.
 */
public class DataToView4 implements Runnable{
    private IRecruitmentInfoService recruitmentInfoService;

    private IBaseCompanySizeViewService baseCompanySizeViewService;//视图4
    private static final Logger logger = Logger.getLogger(DataToView4.class);
    public DataToView4(IRecruitmentInfoService recruitmentInfoService, IBaseCompanySizeViewService baseCompanySizeViewService) {
        this.recruitmentInfoService = recruitmentInfoService;
        this.baseCompanySizeViewService = baseCompanySizeViewService;
    }
    @Override
    public void run() {
        dataToBaseCompanySizeView();
    }
    /**
     * 视图4：基于公司规模的视图
     */
    public void dataToBaseCompanySizeView(){
        logger.info("视图4 start---------------------");
        List<BaseCompanySizeView> list=this.recruitmentInfoService.selectBaseCompanySizeViewCount();//初始数据

        this.baseCompanySizeViewService.deleteAll();//删除原有数据

        this.baseCompanySizeViewService.addList(list);//保存中间数据

        logger.info("视图4 end--------------------");
    }
}
