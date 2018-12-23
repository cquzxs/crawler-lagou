package com.ssh.service.crawler.api;

/**
 * 爬取拉勾网招聘信息
 */
public interface ICrawlerInfoService {

    /**
     * 开始爬取
     *
     * @param isSchool 是否爬取应届生招聘信息
     * @param isDelete 是否删除数据库数据
     * @throws Exception 异常
     */
    void startCrawler(String isSchool,String isDelete) throws  Exception;

    /**
     * 停止爬取
     */
    void stopCrawler();
}