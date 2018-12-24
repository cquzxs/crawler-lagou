package com.ssh.dao.basic.api;

import com.ssh.model.entity.BaseCreateTimeView;

import java.util.List;

public interface IBaseCreateTimeViewDao {
    boolean addList(List<BaseCreateTimeView> list);
    boolean deleteAll();
    List<BaseCreateTimeView> selectAll();

    List<BaseCreateTimeView> selectCount();
    List<BaseCreateTimeView> selectByJobName(String jobName,String isSchool);
}
