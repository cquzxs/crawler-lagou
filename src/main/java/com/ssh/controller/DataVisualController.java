package com.ssh.controller;

import com.alibaba.fastjson.JSON;
import com.ssh.entity.*;
import com.ssh.service.api.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DataVisualController {

    @Resource(name="salaryCompareViewService")
    private ISalaryCompareViewService salaryCompareViewService; //薪资对比视图

    @Resource(name="cityDistributeViewService")
    private ICityDistributeViewService cityDistributeViewService;//城市分布视图

    @Resource(name="baseCreateTimeViewService")
    private IBaseCreateTimeViewService baseCreateTimeViewService;//视图3

    @Resource(name="baseCompanySizeViewService")
    private IBaseCompanySizeViewService baseCompanySizeViewService;//视图4

    @Resource(name="baseSalaryRangeViewService")
    private IBaseSalaryRangeViewService baseSalaryRangeViewService;//视图5

    private static final Logger logger = Logger.getLogger(DataVisualController.class);


    @RequestMapping(value="/getSalaryCompareViewByJobName",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String getSalaryCompareViewByJobName(@RequestParam String jobName,String isSchool){  //通过职位名称获取薪资对比视图
        logger.info("视图1");
        List<SalaryCompareView> list=this.salaryCompareViewService.selectByJobName(jobName,isSchool);
        String res= JSON.toJSONString(list);
        return res;
    }

    @RequestMapping(value="/getCityDistributeViewByJobName",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String getCityDistributeViewByJobName(@RequestParam String jobName,String isSchool){ //通过职位名称获取城市分布视图
        logger.info("视图2");
        List<CityDistributeView> list=this.cityDistributeViewService.selectByJobName(jobName,isSchool);
        String res= JSON.toJSONString(list);
        return res;
    }

    @RequestMapping(value="/selectAvgSalaryByJobName",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String selectAvgSalaryByJobName(String isSchool){   //group by jobName 计算不同职位的平均薪资
        logger.info("视图3");
        List<SalaryCompareView> list=this.salaryCompareViewService.selectAvgSalaryByJobName(isSchool);
        String res= JSON.toJSONString(list);
        return res;
    }

    @RequestMapping("/getDataVisualPage")
    public String getDataVisualPage(){
        logger.info("数据可视化页");
        return "DataVisualPage";
    }

    @RequestMapping(value="/selectBaseCreateTimeViewByJobName",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String selectBaseCreateTimeViewByJobName(String jobName,String isSchool){
        logger.info("视图4");
        List<BaseCreateTimeView> list=this.baseCreateTimeViewService.selectByJobName(jobName,isSchool);
        String res= JSON.toJSONString(list);
        return res;
    }

    @RequestMapping(value="/selectBaseCompanySizeViewByJobName",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String selectBaseCompanySizeViewByJobName(String jobName,String isSchool){
        logger.info("视图5");
        List<BaseCompanySizeView> list=this.baseCompanySizeViewService.selectByJobName(jobName,isSchool);
        List<BaseCompanySizeView> list2=new ArrayList<>();
        //统计
        int[] a={0,0,0,0,0};
        String currentCityName="";
        for (BaseCompanySizeView b:list) {
            if(!b.getCityName().equals(currentCityName)){
                if(!"".equals(currentCityName)){
                    BaseCompanySizeView baseCompanySizeView=new BaseCompanySizeView();
                    baseCompanySizeView.setCityName(currentCityName);
                    String temp="50人以下:"+a[0]+":"+"50-150人:"+a[1]+":"+"150-500人:"+a[2]+":"+"500-2000人:"+a[3]+":"+"2000人以上:"+a[4];
                    baseCompanySizeView.setCompanySize(temp);
                    list2.add(baseCompanySizeView);
                    for (int i = 0; i < a.length; i++) {
                        a[i]=0;
                    }
                }
                currentCityName=b.getCityName();
            }
            if( "10-50人".equals(b.getCompanySize()) || "15-50人".equals(b.getCompanySize()) || "少于15人".equals(b.getCompanySize())){
                a[0]=a[0]+b.getCount();
            }
            if( "50-150人".equals(b.getCompanySize()) ){
                a[1]=b.getCount();
            }
            if( "150-500人".equals(b.getCompanySize()) ){
                a[2]=b.getCount();
            }
            if( "500-2000人".equals(b.getCompanySize()) ){
                a[3]=b.getCount();
            }
            if( "2000人以上".equals(b.getCompanySize()) ){
                a[4]=b.getCount();
            }
        }
        String res= JSON.toJSONString(list2);
        //System.out.println(res);
        return res;
    }

    @RequestMapping(value="/selectBaseSalaryRangeViewByJobName",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String selectBaseSalaryRangeViewByJobName(String jobName,String isSchool){
        logger.info("视图6");
        List<BaseSalaryRangeView> list=this.baseSalaryRangeViewService.selectByJobName(jobName,isSchool);
        String res= JSON.toJSONString(list);
        return res;
    }
}
