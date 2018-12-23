package com.ssh.service.impl;

import com.ssh.dao.api.IBaseCreateTimeViewDao;
import com.ssh.entity.BaseCreateTimeView;
import com.ssh.service.api.IBaseCreateTimeViewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("baseCreateTimeViewService")
public class BaseCreateTimeViewServiceImpl implements IBaseCreateTimeViewService{
    @Resource(name="baseCreateTimeViewDao")
    private IBaseCreateTimeViewDao baseCreateTimeViewDao;

    @Override
    public boolean addList(List<BaseCreateTimeView> list) {
        return this.baseCreateTimeViewDao.addList(list);
    }

    @Override
    public boolean deleteAll() {
        return this.baseCreateTimeViewDao.deleteAll();
    }

    @Override
    public List<BaseCreateTimeView> selectAll() {
        return this.baseCreateTimeViewDao.selectAll();
    }

    @Override
    public List<BaseCreateTimeView> selectCount() {
        return this.baseCreateTimeViewDao.selectCount();
    }

    @Override
    public List<BaseCreateTimeView> selectByJobName(String jobName,String isSchool) {
        return this.baseCreateTimeViewDao.selectByJobName(jobName,isSchool);
    }
}
