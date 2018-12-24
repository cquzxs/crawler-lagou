package com.ssh.service.basic.impl;

import com.ssh.dao.basic.api.IBaseSalaryRangeViewDao;
import com.ssh.entity.BaseSalaryRangeView;
import com.ssh.service.basic.api.IBaseSalaryRangeViewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("baseSalaryRangeViewService")
public class BaseSalaryRangeViewServiceImpl implements IBaseSalaryRangeViewService{
    @Resource(name="baseSalaryRangeViewDao")
    private IBaseSalaryRangeViewDao baseSalaryRangeViewDao;

    @Override
    public boolean addList(List<BaseSalaryRangeView> list) {
        return this.baseSalaryRangeViewDao.addList(list);
    }

    @Override
    public boolean deleteAll() {
        return this.baseSalaryRangeViewDao.deleteAll();
    }

    @Override
    public List<BaseSalaryRangeView> selectAll() {
        return this.baseSalaryRangeViewDao.selectAll();
    }

    @Override
    public List<BaseSalaryRangeView> selectByJobName(String jobName,String isSchool) {
        return this.baseSalaryRangeViewDao.selectByJobName(jobName,isSchool);
    }
}
