package com.ssh.dao.basic.api;

import com.ssh.model.entity.CityDistributeView;

import java.util.List;

public interface ICityDistributeViewDao {

    boolean addList(List<CityDistributeView> list);

    boolean deleteAll();

    List<CityDistributeView> selectAll();
    List<CityDistributeView> selectByJobName(String jobName,String isSchool);
    List<CityDistributeView> selectCount();

}
