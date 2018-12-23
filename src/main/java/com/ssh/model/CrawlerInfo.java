package com.ssh.model;

public class CrawlerInfo {
    private String status;//爬取状态
    private String startTime;//爬虫开始时间
    private String endTime;//爬虫结束时间
    private String duration;//爬取时长


    private int dataCount;//当前爬取数据总数
    private  String crawlerSpeed="";//爬取速度
    private int requestTimes;//请求次数
    private int unuseRequestCount=0; //无效请求数

    private int requestThreadCount;//任务总数
    private int unuseTaskCount=0;//无效任务数
    private int executedTaskCount=0;//已执行任务数

    private String isSchool;//是否是应届生
    private String isDelete;//是否删除现有数据

    public String getIsSchool() {
        return isSchool;
    }

    public void setIsSchool(String isSchool) {
        this.isSchool = isSchool;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public String getCrawlerSpeed() {
        return crawlerSpeed;
    }

    public void setCrawlerSpeed(String crawlerSpeed) {
        this.crawlerSpeed = crawlerSpeed;
    }

    public int getRequestTimes() {
        return requestTimes;
    }

    public void setRequestTimes(int requestTimes) {
        this.requestTimes = requestTimes;
    }

    public int getUnuseRequestCount() {
        return unuseRequestCount;
    }

    public void setUnuseRequestCount(int unuseRequestCount) {
        this.unuseRequestCount = unuseRequestCount;
    }

    public int getRequestThreadCount() {
        return requestThreadCount;
    }

    public void setRequestThreadCount(int requestThreadCount) {
        this.requestThreadCount = requestThreadCount;
    }

    public int getUnuseTaskCount() {
        return unuseTaskCount;
    }

    public void setUnuseTaskCount(int unuseTaskCount) {
        this.unuseTaskCount = unuseTaskCount;
    }

    public int getExecutedTaskCount() {
        return executedTaskCount;
    }

    public void setExecutedTaskCount(int executedTaskCount) {
        this.executedTaskCount = executedTaskCount;
    }
}
