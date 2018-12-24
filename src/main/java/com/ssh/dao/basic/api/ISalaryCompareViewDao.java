package com.ssh.dao.basic.api;


import com.ssh.model.entity.SalaryCompareView;

import java.util.List;

public interface ISalaryCompareViewDao {

    boolean addList(List<SalaryCompareView> list);

    boolean deleteAll();

    List<SalaryCompareView> selectAll();
    List<SalaryCompareView> selectByJobName(String jobName,String isSchool);
    List<SalaryCompareView> selectAvgSalaryByJobName(String isSchool);
    List<SalaryCompareView> selectAvgSalary();

}
