package com.ssh.controller;

import com.alibaba.fastjson.JSON;
import com.ssh.model.entity.RecruitmentInfo;
import com.ssh.model.MyPage;
import com.ssh.service.basic.api.IRecruitmentInfoService;
import com.ssh.service.util.GlobelData;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用于处理分页
 */
@Controller
public class PageController {
    @Resource(name="recruitmentInfoService")
    private IRecruitmentInfoService recruitmentInfoService;//招聘信息表
    private String jobName="Java";
    private String isSchool="1";
    private String isFuzzyQuery="0";

    private static final Logger logger = Logger.getLogger(PageController.class);

    @RequestMapping(value="/showInfoList",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String showInfoList(String jobName,String isSchool,String isFuzzyQuery){  //第一页,需要初始化页面信息
        String res="";
        int maxResult=GlobelData.pageSize;
        int firstResult=0;
        this.jobName=jobName;
        this.isSchool=isSchool;
        this.isFuzzyQuery=isFuzzyQuery;

        //初始化数据总数
        GlobelData.totalCount=this.recruitmentInfoService.selectTotalCount(jobName,isSchool,isFuzzyQuery);
        //初始化总页数
        if(GlobelData.pageSize!=0){
            GlobelData.pageCount=GlobelData.totalCount/GlobelData.pageSize+1;
        }
        //初始化当前页码
        GlobelData.currentPage=1;

        //获取数据
        List<RecruitmentInfo> list=this.recruitmentInfoService.selectInfoWithLimit(jobName,maxResult,firstResult,isSchool,isFuzzyQuery);
        res= JSON.toJSONString(list);
        //System.out.println(res);
        return res;
    }
    @RequestMapping(value="/prePage",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String prePage(){    //上一页
        String res="";
        if(GlobelData.currentPage>1){ //页码大于1，当前页减1；页码等于1，当前页不变
            GlobelData.currentPage--;
        }
        res=getJsonData();
        return res;
    }
    public String getJsonData(){    //根据分页信息，获取数据
        String res="";
        int firstResult=(GlobelData.currentPage-1)*GlobelData.pageSize;
        List<RecruitmentInfo> list=this.recruitmentInfoService.selectInfoWithLimit(jobName,GlobelData.pageSize,firstResult,this.isSchool,this.isFuzzyQuery);
        res= JSON.toJSONString(list);
        //System.out.println(res);
        return res;
    }

    @RequestMapping(value="/nextPage",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String nextPage(){  //下一页
        String res="";
        if(GlobelData.currentPage<GlobelData.pageCount){   //页码小于总页数，当前页加1；页码等于总页数，当前页不变
            GlobelData.currentPage++;
        }
        res=getJsonData();
        return res;
    }
    @RequestMapping(value="/firstPage",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String firstPage(){   //首页
        String res="";
        GlobelData.currentPage=1;   //当前页设为1
        res=getJsonData();
        return res;
    }
    @RequestMapping(value="/tailPage",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String tailPage(){    //尾页
        String res="";
        GlobelData.currentPage=GlobelData.pageCount;   //当前页设为总页数
        res=getJsonData();
        return res;
    }
    @RequestMapping(value="/goToCertainPage",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String goToCertainPage(String pageNumber){   //跳转到具体某一页
        String res="";
        if(Integer.parseInt(pageNumber)<=GlobelData.pageCount && Integer.parseInt(pageNumber)>=1){
            GlobelData.currentPage=Integer.parseInt(pageNumber);//pageNumber在1到总页数之间，将当前页设为pageNumber
        }
        res=getJsonData();
        return res;
    }
    @RequestMapping(value="/setPageSize",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String setPageSize(String pageSize){   //设置页面大小
        String res="";
        if(Integer.parseInt(pageSize)<=GlobelData.totalCount && Integer.parseInt(pageSize)>=1){
            GlobelData.pageSize=Integer.parseInt(pageSize);//设置页面大小
            GlobelData.currentPage=1;//将当前页设为1
            GlobelData.pageCount= GlobelData.totalCount/GlobelData.pageSize+1;//计算总页数
        }
        res=getJsonData();
        return res;
    }

    @RequestMapping(value="/showPageInfo",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String showPageInfo(){     //展示分页信息
        String res="";
        MyPage myPage=new MyPage();
        myPage.setCurrentPage(GlobelData.currentPage);
        myPage.setPageCount(GlobelData.pageCount);
        myPage.setPageSize(GlobelData.pageSize);
        myPage.setTotalCount(GlobelData.totalCount);
        res= JSON.toJSONString(myPage);
        return res;
    }
}
