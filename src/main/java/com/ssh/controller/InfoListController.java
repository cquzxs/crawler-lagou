package com.ssh.controller;

import com.alibaba.fastjson.JSON;
import com.ssh.model.constants.MyConstants;
import com.ssh.service.basic.api.IRecruitmentInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@Controller
public class InfoListController {
    @Resource(name="recruitmentInfoService")
    private IRecruitmentInfoService recruitmentInfoService;//招聘信息表

    private static final Logger logger = Logger.getLogger(InfoListController.class);

    @RequestMapping("/getInfoListPage")
    public String getInfoListPage(){
        logger.info("招聘信息展示页");
        return "InfoListPage";
    }

    @RequestMapping(value="/getJobList",produces="html/text;charset=UTF-8")
    @ResponseBody
    public String getJobList(){
        logger.info("获取职位列表");
        String res="";
        List<String> list=this.recruitmentInfoService.getJobList();
        HashMap<String,String> map=new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String key=list.get(i);
            try {
                String value= URLEncoder.encode(key, MyConstants.charset);
                map.put(key,value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        res= JSON.toJSONString(map);
        return res;
    }

}
