package com.ssh.controller;

import com.ssh.service.crawler.api.ICrawlerInfoService;
import com.ssh.service.views.api.IViewsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class CrawlerController {

    private static final Logger logger = Logger.getLogger(CrawlerController.class);

    @Resource(name="crawlerInfoService")
    private ICrawlerInfoService crawlerInfoService;//爬虫服务
    @Resource(name = "viewsService")
    private IViewsService viewsService;  //视图更新服务


    @RequestMapping(value="/startCrawler",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String startCrawler(String isSchool,String isDelete) throws Exception {
        try{
            this.crawlerInfoService.startCrawler(isSchool, isDelete);
        }catch (Exception e){
            logger.info("开始爬取任务失败", e);
        }
        return "ok";
    }

    @RequestMapping("/getCrawlerPage")
    public String getCrawlerPage(){
        logger.info("爬虫管理页");
        return "CrawlerPage";
    }


    @RequestMapping(value="/stopCrawler",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String stopCrawler(){
        logger.info("停止爬取招聘信息");
        this.crawlerInfoService.stopCrawler();
        return "ok";
    }

    @RequestMapping(value="/updateViews",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String updateViews(){     //返回json数据
        this.viewsService.updateViews();
        return "ok";
    }
}
