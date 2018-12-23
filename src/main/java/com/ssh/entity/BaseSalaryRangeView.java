package com.ssh.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 基于薪资范围的视图
 */
@Data
@Entity   //hibernate之实体配置
@Table(name = "BaseSalaryRangeView")
public class BaseSalaryRangeView {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @Column(name = "jobName")
    private String jobName;//职位名称
    @Column(name = "salaryRange")
    private String salaryRange;//薪资范围
    @Column(name = "count")
    private int count;//group by jobName,salaryRange 个数
    @Column(name = "isSchool")
    private int isSchool;

    public BaseSalaryRangeView() {
    }

    public BaseSalaryRangeView(String jobName, String salaryRange, int count,int isSchool) {
        this.jobName = jobName;
        this.salaryRange = salaryRange;
        this.count = count;
        this.isSchool = isSchool;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIsSchool() {
        return isSchool;
    }

    public void setIsSchool(int isSchool) {
        this.isSchool = isSchool;
    }
}
