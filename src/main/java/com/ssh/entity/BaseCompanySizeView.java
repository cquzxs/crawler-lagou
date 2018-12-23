package com.ssh.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 基于公司规模的视图
 */
@Data
@Entity   //hibernate之实体配置
@Table(name = "BaseCompanySizeView")
public class BaseCompanySizeView {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @Column(name = "cityName")
    private String cityName;//城市名称
    @Column(name = "jobName")
    private String jobName;//职位名称
    @Column(name = "companySize")
    private String companySize;//公司规模
    @Column(name = "count")
    private int count;//group by 公司规模，职位名称，城市名称 的个数
    @Column(name = "isSchool")
    private int isSchool;

    public BaseCompanySizeView() {
    }

    public BaseCompanySizeView(String cityName, String jobName, String companySize, int count,int isSchool) {
        this.cityName = cityName;
        this.jobName = jobName;
        this.companySize = companySize;
        this.count = count;
        this.isSchool = isSchool;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
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
