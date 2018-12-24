package com.ssh.service.basic.impl;

import com.ssh.dao.basic.api.IBaseCompanySizeViewDao;
import com.ssh.entity.BaseCompanySizeView;
import com.ssh.service.basic.api.IBaseCompanySizeViewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("baseCompanySizeViewService")
public class BaseCompanySizeViewServiceImpl implements IBaseCompanySizeViewService{
    @Resource(name="baseCompanySizeViewDao")
    private IBaseCompanySizeViewDao baseCompanySizeViewDao;

    @Override
    public boolean addList(List<BaseCompanySizeView> list) {
        return this.baseCompanySizeViewDao.addList(list);
    }

    @Override
    public boolean deleteAll() {
        return this.baseCompanySizeViewDao.deleteAll();
    }

    @Override
    public List<BaseCompanySizeView> selectAll() {
        return this.baseCompanySizeViewDao.selectAll();
    }

    @Override
    public List<BaseCompanySizeView> selectByJobName(String jobName,String isSchool) {
        return this.baseCompanySizeViewDao.selectByJobName(jobName,isSchool);
    }
}
