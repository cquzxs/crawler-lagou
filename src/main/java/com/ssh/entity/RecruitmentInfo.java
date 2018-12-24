package com.ssh.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//招聘信息表
@Data
@Entity   //hibernate之实体配置
@Table(name = "recruitment_info")
public class RecruitmentInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;//招聘信息id,即主键id
    @Column(name = "companyName")
    private String companyName;//公司名称
    @Column(name = "jobName")
    private String jobName;//岗位名称
    @Column(name = "cityName")
    private String cityName;//工作城市
    @Column(name = "salaryRange")
    private String salaryRange;//薪资范围
    @Column(name = "followsCount")
    private int followsCount;//1 应届生 0 非应届生
    @Column(name = "detailUrl")
    private String detailUrl;//详情页面链接
    @Column(name = "createTime")
    private String createTime;//发布时间
    @Column(name = "companySize")
    private String companySize;//公司规模
    @Column(name = "positionAdvantage")
    private String positionAdvantage;//职位优势

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getPositionAdvantage() {
        return positionAdvantage;
    }

    public void setPositionAdvantage(String positionAdvantage) {
        this.positionAdvantage = positionAdvantage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public int getFollowsCount() {
        return followsCount;
    }

    public void setFollowsCount(int followsCount) {
        this.followsCount = followsCount;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RecruitmentInfo{" +
                "id='" + id + '\'' +
                ", companyName='" + companyName + '\'' +
                ", jobName='" + jobName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", salaryRange='" + salaryRange + '\'' +
                ", followsCount=" + followsCount +
                ", detailUrl='" + detailUrl + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
