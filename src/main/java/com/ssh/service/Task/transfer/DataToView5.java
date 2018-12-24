package com.ssh.service.Task.transfer;

import com.ssh.entity.BaseSalaryRangeView;
import com.ssh.service.basic.api.IBaseSalaryRangeViewService;
import com.ssh.service.basic.api.IRecruitmentInfoService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Name:SSH
 * File Name:DataToView5
 * Package Name:com.ssh.service.Task.transfer
 * Date:2018/5/28 16:54
 * Author:zengxueshan
 * Description:
 * Copyright (c) 2018, 重庆云凯科技有限公司 All Rights Reserved.
 */
public class DataToView5 implements Runnable{
    private IRecruitmentInfoService recruitmentInfoService;

    private IBaseSalaryRangeViewService baseSalaryRangeViewService;//视图5
    private static final Logger logger = Logger.getLogger(DataToView5.class);
    public DataToView5(IRecruitmentInfoService recruitmentInfoService, IBaseSalaryRangeViewService baseSalaryRangeViewService) {
        this.recruitmentInfoService = recruitmentInfoService;
        this.baseSalaryRangeViewService = baseSalaryRangeViewService;
    }
    @Override
    public void run() {
        dataToBaseSalaryRangeView();
    }
    /**
     * 视图5：基于薪资范围的视图
     */
    private void dataToBaseSalaryRangeView(){
        logger.info("视图5 start---------------------");
        System.out.println("查询所有招聘信息中...");
        List<BaseSalaryRangeView> list=this.recruitmentInfoService.selectBaseSalaryRangeViewCount();//初始数据
        System.out.println("查询所有招聘信息完成.");

        System.out.println("删除原有数据中...");
        this.baseSalaryRangeViewService.deleteAll();//删除原有数据
        System.out.println("删除原有数据完成.");

        List<BaseSalaryRangeView> viewList=new ArrayList<>();
        String currentJobName="";
        String[] a={"4k以下","4k-6k","6k-8k","8k-12k","12k-15k","15k-20k","20k以上"};
        int[] b={0,0,0,0,0,0,0};
        for (BaseSalaryRangeView s:list) {  //list是按jobName排序的
            if(s.getIsSchool()==1){
                if( !currentJobName.equals(s.getJobName()) ){
                    if(!"".equals(currentJobName)){
                        for (int i = 0; i < a.length; i++) {
                            BaseSalaryRangeView baseSalaryRangeView=new BaseSalaryRangeView(currentJobName,a[i],b[i],1);
                            viewList.add(baseSalaryRangeView);
                        }
                        for (int i = 0; i < b.length; i++) {
                            b[i]=0;
                        }
                    }
                }
                currentJobName=s.getJobName();
                int temp=avgSalary(s.getSalaryRange());
                if(temp<4){
                    b[0]=b[0]+s.getCount();
                }else if(temp<6){
                    b[1]=b[1]+s.getCount();
                }else if(temp<8){
                    b[2]=b[2]+s.getCount();
                }else if(temp<12){
                    b[3]=b[3]+s.getCount();
                }else if(temp<15){
                    b[4]=b[4]+s.getCount();
                }else if(temp<20){
                    b[5]=b[5]+s.getCount();
                }else{
                    b[6]=b[6]+s.getCount();
                }
            }
        }
        currentJobName="";
        for (int i = 0; i < b.length; i++) {
            b[i]=0;
        }
        for (BaseSalaryRangeView s:list) {  //list是按jobName排序的
            if(s.getIsSchool()==0){
                if( !currentJobName.equals(s.getJobName()) ){
                    if(!"".equals(currentJobName)){
                        for (int i = 0; i < a.length; i++) {
                            BaseSalaryRangeView baseSalaryRangeView=new BaseSalaryRangeView(currentJobName,a[i],b[i],0);
                            viewList.add(baseSalaryRangeView);
                        }
                        for (int i = 0; i < b.length; i++) {
                            b[i]=0;
                        }
                    }
                }
                currentJobName=s.getJobName();
                int temp=avgSalary(s.getSalaryRange());
                if(temp<=4){
                    b[0]=b[0]+s.getCount();
                }else if(temp<6){
                    b[1]=b[1]+s.getCount();
                }else if(temp<8){
                    b[2]=b[2]+s.getCount();
                }else if(temp<12){
                    b[3]=b[3]+s.getCount();
                }else if(temp<15){
                    b[4]=b[4]+s.getCount();
                }else if(temp<20){
                    b[5]=b[5]+s.getCount();
                }else{
                    b[6]=b[6]+s.getCount();
                }
            }
        }
        System.out.println("保存最终数据中...");
        this.baseSalaryRangeViewService.addList(viewList);//保存中间数据
        System.out.println("保存最终数据完成.");

        logger.info("视图5 end--------------------");
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
