package com.ssh.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 基于发布时间的视图
 */
@Data
@Entity   //hibernate之实体配置
@Table(name = "BaseCreateTimeView")
public class BaseCreateTimeView {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @Column(name = "jobName")
    private String jobName;//职位名称
    @Column(name = "count")
    private int count;//某一时间的某一职位数
    @Column(name = "createTime")
    private String createTime;//创建时间
    @Column(name = "isSchool")
    private int isSchool;

    public BaseCreateTimeView() {
    }

    public BaseCreateTimeView(String jobName, int count, String createTime,int isSchool) {
        this.jobName = jobName;
        this.count = count;
        this.createTime = createTime;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsSchool() {
        return isSchool;
    }

    public void setIsSchool(int isSchool) {
        this.isSchool = isSchool;
    }
}
