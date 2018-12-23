package com.ssh.util;

import com.ssh.constants.MyConstants;
import com.ssh.model.api.AbstractModel;
import com.ssh.model.ModelType;
import com.ssh.model.RequestParams;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.*;

/**
 * 用于请求拉勾网首页https://www.lagou.com/
 * 获取构造url所需的jobName
 */
public class GetUrls {
    public static HashMap<String,String> getUrls() throws Exception {
        HashMap<String,String> map=new HashMap<>();//存储职位类别，key为jobName，value为key的url编码
        ResourceBundle resourceBundle = ResourceBundle.getBundle("first-page");
        RequestParams requestParams;
        requestParams=new RequestParams(
                resourceBundle.getString("FIRST_TARGET"),
                resourceBundle.getString("FIRST_COOKIE"),
                resourceBundle.getString("FIRST_REFERER"),
                resourceBundle.getString("FIRST_CHARSET"),
                resourceBundle.getString("FIRST_USER_AGENT"),
                "",
                ""
        );
        //请求网页
        AbstractModel abstractModel=RequestUtil.requestUri(requestParams);//请求拉勾网首页
        //解析数据
        if(abstractModel != null && abstractModel.getModelType()== ModelType.HTML){   //请求拉勾网首页响应的是html
            Document doc=(Document)abstractModel.getSourceEntity();
            Elements elements=doc.getElementsByClass("menu_box");//职位列表div
            for (int k = 0; k < elements.size(); k++) {
                Elements elements1=elements.get(k).getElementsByTag("dd");//列表项首部dd
                for (int i = 0; i < elements1.size(); i++) {
                    Element element=elements1.get(i);//单个dd
                    Elements elements2=element.getElementsByTag("a");//dd中的a标签
                    for (int j = 0; j < elements2.size(); j++) {
                        Element element1=elements2.get(j);//单个a标签
                        String value=element1.text();
                        String value2= URLEncoder.encode(value, MyConstants.charset);
                        map.put(value,value2);
                    }
                }
            }
        }
        return map;
    }
}
