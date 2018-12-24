package com.ssh.service.basic.api;

import com.ssh.model.entity.BaseSalaryRangeView;

import java.util.List;

public interface IBaseSalaryRangeViewService {
    boolean addList(List<BaseSalaryRangeView> list);
    boolean deleteAll();
    List<BaseSalaryRangeView> selectAll();

    List<BaseSalaryRangeView> selectByJobName(String jobName,String isSchool);
}
