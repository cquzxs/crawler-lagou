package com.ssh.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//城市分布视图
@Data
@Entity   //hibernate之实体配置
@Table(name = "CityDistributeView")
public class CityDistributeView {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Column(name = "jobName")
    private String jobName;

    @Column(name = "cityName")
    private String cityName;

    @Column(name = "count")
    private int count;//个数

    @Column(name = "ratio")
    private double ratio;//占比

    @Column(name = "isSchool")
    private int isSchool;

    public CityDistributeView() {
    }

    public CityDistributeView(String jobName, String cityName, int count,int isSchool) {
        this.jobName = jobName;
        this.cityName = cityName;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public int getIsSchool() {
        return isSchool;
    }

    public void setIsSchool(int isSchool) {
        this.isSchool = isSchool;
    }

    @Override
    public String toString() {
        return "CityDistributeView{" +
                "id='" + id + '\'' +
                ", jobName='" + jobName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", count=" + count +
                ", ratio=" + ratio +
                '}';
    }
}
