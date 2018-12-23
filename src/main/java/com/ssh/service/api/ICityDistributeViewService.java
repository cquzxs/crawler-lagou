package com.ssh.service.api;


import com.ssh.entity.CityDistributeView;

import java.util.List;

public interface ICityDistributeViewService {

    boolean addList(List<CityDistributeView> list);

    boolean deleteAll();

    List<CityDistributeView> selectAll();
    List<CityDistributeView> selectByJobName(String jobName,String isSchool);
    List<CityDistributeView> selectCount();
}
