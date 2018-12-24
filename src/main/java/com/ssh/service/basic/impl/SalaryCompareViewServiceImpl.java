package com.ssh.service.basic.impl;

import com.ssh.dao.basic.api.ISalaryCompareViewDao;
import com.ssh.entity.SalaryCompareView;
import com.ssh.service.basic.api.ISalaryCompareViewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("salaryCompareViewService")
public class SalaryCompareViewServiceImpl implements ISalaryCompareViewService{

    @Resource(name="salaryCompareViewDao")
    private ISalaryCompareViewDao salaryCompareViewDao;


    @Override
    public boolean addList(List<SalaryCompareView> list) {
        return this.salaryCompareViewDao.addList(list);
    }

    @Override
    public boolean deleteAll() {
        return this.salaryCompareViewDao.deleteAll();
    }


    @Override
    public List<SalaryCompareView> selectAll() {
        return this.salaryCompareViewDao.selectAll();
    }

    @Override
    public List<SalaryCompareView> selectByJobName(String jobName,String isSchool) {
        return this.salaryCompareViewDao.selectByJobName(jobName,isSchool);
    }

    @Override
    public List<SalaryCompareView> selectAvgSalary() {
        return this.salaryCompareViewDao.selectAvgSalary();
    }

    @Override
    public List<SalaryCompareView> selectAvgSalaryByJobName(String isSchool) {
        return this.salaryCompareViewDao.selectAvgSalaryByJobName(isSchool);
    }
}
