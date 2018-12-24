package com.ssh.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//薪水对比视图
@Data
@Entity   //hibernate之实体配置
@Table(name = "salaryCompareView")
public class SalaryCompareView {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Column(name = "jobName")
    private String jobName;

    @Column(name = "cityName")
    private String cityName;

    @Column(name = "avgSarary")
    private int avgSarary;

    @Column(name = "isSchool")
    private int isSchool;

    public SalaryCompareView() {
    }

    public SalaryCompareView(String jobName, String cityName, int avgSarary,int isSchool) {
        this.jobName = jobName;
        this.cityName = cityName;
        this.avgSarary = avgSarary;
        this.isSchool = isSchool;
    }

    public SalaryCompareView(String jobName, int avgSarary) {
        this.jobName = jobName;
        this.avgSarary = avgSarary;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getAvgSarary() {
        return avgSarary;
    }

    public void setAvgSarary(int avgSarary) {
        this.avgSarary = avgSarary;
    }

    public int getIsSchool() {
        return isSchool;
    }

    public void setIsSchool(int isSchool) {
        this.isSchool = isSchool;
    }

    @Override
    public String toString() {
        return "SalaryCompareView{" +
                "id='" + id + '\'' +
                ", jobName='" + jobName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", avgSarary='" + avgSarary + '\'' +
                '}';
    }
}
