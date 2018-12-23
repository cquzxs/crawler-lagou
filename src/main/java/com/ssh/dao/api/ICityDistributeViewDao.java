package com.ssh.dao.api;

import com.ssh.entity.CityDistributeView;
import com.ssh.entity.SalaryCompareView;

import java.util.List;

public interface ICityDistributeViewDao {

    boolean addList(List<CityDistributeView> list);

    boolean deleteAll();

    List<CityDistributeView> selectAll();
    List<CityDistributeView> selectByJobName(String jobName,String isSchool);
    List<CityDistributeView> selectCount();

}
