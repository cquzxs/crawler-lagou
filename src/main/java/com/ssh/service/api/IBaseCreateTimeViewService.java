package com.ssh.service.api;

import com.ssh.entity.BaseCreateTimeView;

import java.util.List;

public interface IBaseCreateTimeViewService {
    boolean addList(List<BaseCreateTimeView> list);
    boolean deleteAll();
    List<BaseCreateTimeView> selectAll();

    List<BaseCreateTimeView> selectCount();
    List<BaseCreateTimeView> selectByJobName(String jobName,String isSchool);
}
