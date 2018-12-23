package com.ssh.service.api;


import com.ssh.entity.BaseCompanySizeView;
import com.ssh.entity.BaseSalaryRangeView;
import com.ssh.entity.RecruitmentInfo;

import java.util.List;

public interface IRecruitmentInfoSevice {

    /**
     * 删除所有招聘信息
     */
    boolean deleteAllRecruitmentInfo();

    /**
     * 添加一组招聘信息
     */
    boolean addRecruitmentInfoList(List<RecruitmentInfo> recruitmentInfoList);

    /**
     * 获取职位列表
     */
    List<String> getJobList();

    /**
     * 通过jobName查询招聘信息，并且带有limit限制
     */
    List<RecruitmentInfo> selectInfoWithLimit(String job,int maxResult,int firstResult,String isSchool,String isFuzzyQuery);

    /**
     * 通过jobName查询招聘信息总数
     */
    int selectTotalCount(String job,String isSchool,String isFuzzyQuery);
    /**
     * 查询所有招聘信息
     */
    List<RecruitmentInfo> selectAllRecruitmentInfo();


    List<BaseCompanySizeView> selectBaseCompanySizeViewCount();

    List<BaseSalaryRangeView> selectBaseSalaryRangeViewCount();


}
