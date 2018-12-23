package com.ssh.dao.api;

import com.ssh.entity.BaseCompanySizeView;

import java.util.List;

public interface IBaseCompanySizeViewDao {
    boolean addList(List<BaseCompanySizeView> list);
    boolean deleteAll();
    List<BaseCompanySizeView> selectAll();

    List<BaseCompanySizeView> selectByJobName(String jobName,String isSchool);
}
