package com.ssh.service.basic.impl;


import com.ssh.dao.basic.api.IRecruitmentInfoTableDao;
import com.ssh.entity.BaseCompanySizeView;
import com.ssh.entity.BaseSalaryRangeView;
import com.ssh.entity.RecruitmentInfo;
import com.ssh.service.basic.api.IRecruitmentInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("recruitmentInfoService")
public class RecruitmentInfoServiceImpl implements IRecruitmentInfoService {
    @Resource(name="recruitmentInfoTableDao")
    private IRecruitmentInfoTableDao recruitmentInfoTableDao;
    @Override
    public boolean addRecruitmentInfoList(List<RecruitmentInfo> recruitmentInfoList) {
        return this.recruitmentInfoTableDao.addRecruitmentInfoList(recruitmentInfoList);
    }

    @Override
    public boolean deleteAllRecruitmentInfo() {
        return this.recruitmentInfoTableDao.deleteAllRecruitmentInfo();
    }

    @Override
    public List<RecruitmentInfo> selectAllRecruitmentInfo() {
        return this.recruitmentInfoTableDao.selectAllRecruitmentInfo();
    }

    @Override
    public List<BaseCompanySizeView> selectBaseCompanySizeViewCount() {
        return this.recruitmentInfoTableDao.selectBaseCompanySizeViewCount();
    }

    @Override
    public List<BaseSalaryRangeView> selectBaseSalaryRangeViewCount() {
        return this.recruitmentInfoTableDao.selectBaseSalaryRangeViewCount();
    }


    @Override
    public List<String> getJobList() {
        return this.recruitmentInfoTableDao.getJobList();
    }

    @Override
    public List<RecruitmentInfo> selectInfoWithLimit(String job, int maxResult, int firstResult,String isSchool,String isFuzzyQuery) {
        return this.recruitmentInfoTableDao.selectInfoWithLimit(job,maxResult,firstResult,isSchool,isFuzzyQuery);
    }

    @Override
    public int selectTotalCount(String job,String isSchool,String isFuzzyQuery) {
        return this.recruitmentInfoTableDao.selectTotalCount(job,isSchool,isFuzzyQuery);
    }
}
