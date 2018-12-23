package com.ssh.service.impl;

import com.ssh.dao.api.ICityDistributeViewDao;
import com.ssh.entity.CityDistributeView;
import com.ssh.service.api.ICityDistributeViewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("cityDistributeViewService")
public class CityDistributeViewServiceImpl implements ICityDistributeViewService{
    @Resource(name="cityDistributeViewDao")
    private ICityDistributeViewDao cityDistributeViewDao;

    @Override
    public boolean addList(List<CityDistributeView> list) {
        return this.cityDistributeViewDao.addList(list);
    }

    @Override
    public boolean deleteAll() {
        return this.cityDistributeViewDao.deleteAll();
    }


    @Override
    public List<CityDistributeView> selectAll() {
        return this.cityDistributeViewDao.selectAll();
    }

    @Override
    public List<CityDistributeView> selectByJobName(String jobName,String isSchool) {
        return this.cityDistributeViewDao.selectByJobName(jobName,isSchool);
    }

    @Override
    public List<CityDistributeView> selectCount() {
        return this.cityDistributeViewDao.selectCount();
    }
}
