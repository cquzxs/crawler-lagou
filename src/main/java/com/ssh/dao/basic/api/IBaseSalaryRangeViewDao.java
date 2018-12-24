package com.ssh.dao.basic.api;

import com.ssh.entity.BaseSalaryRangeView;

import java.util.List;

public interface IBaseSalaryRangeViewDao {
    boolean addList(List<BaseSalaryRangeView> list);
    boolean deleteAll();
    List<BaseSalaryRangeView> selectAll();

    List<BaseSalaryRangeView> selectByJobName(String jobName,String isSchool);
}
