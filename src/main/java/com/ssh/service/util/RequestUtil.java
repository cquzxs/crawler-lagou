package com.ssh.service.util;

import com.ssh.model.*;
import com.ssh.model.crawler.api.AbstractModel;
import com.ssh.model.crawler.impl.HtmlModel;
import com.ssh.model.crawler.impl.JSONArrayModel;
import com.ssh.model.crawler.impl.JSONObjectModel;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.InputStream;
import java.net.URI;
import java.util.regex.Pattern;

/**
 * 执行请求所用到的工具类
 */
public class RequestUtil {
    //使用httpclient请求uri，获取响应内容,并将响应内容封装为AbstractModel
    public static AbstractModel requestUri(RequestParams requestParams) throws Exception {
        //初始化getMethod
        GetMethod getMethod=initGetMethod(requestParams);
        //初始化client
        HttpClient client=initHttpClient();
        //执行请求
        if(client!=null && getMethod!=null){
            client.executeMethod(getMethod);
            //获取响应数据
            InputStream inputStream=getMethod.getResponseBodyAsStream();
            String data = IOUtils.toString(inputStream, getMethod.getParams().getContentCharset());
            inputStream.close();
            //释放http连接
            getMethod.releaseConnection();
            //获取请求uri
            String uriStr = getMethod.getURI().getURI();
            URI uri = new URI(uriStr);
            //数据封装
            AbstractModel model=dataToModel(data,uri);
            return model;
        }
        return null;
    }

    private static GetMethod initGetMethod(RequestParams requestParams){
        //初始化getMethod
        GetMethod getMethod = new GetMethod(requestParams.getTarget());
        getMethod.setRequestHeader("Cookie", requestParams.getCookie());
        getMethod.setRequestHeader("Referer", requestParams.getReferer());
        getMethod.setRequestHeader("User-Agent", requestParams.getUserAgent());
        getMethod.getParams().setContentCharset(requestParams.getCharset());
        return getMethod;
    }
    private static HttpClient initHttpClient(){
        //初始化client
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();//http连接池
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setDefaultMaxConnectionsPerHost(100);
        params.setMaxTotalConnections(100);
        connectionManager.setParams(params);
        HttpClient client = new HttpClient(connectionManager);
        return client;
    }
    //数据封装
    private static AbstractModel dataToModel(String data,URI uri){
        AbstractModel model = null;
        Pattern bracketPattern = Pattern.compile("^\\[.*\\]$");//中括号，JSONArray
        Pattern bracesPattern = Pattern.compile("^\\{.*\\}$");//花括号，JSONObject
        if (bracketPattern.matcher(data).matches()) {
            JSONArray jsonArray = new JSONArray(data);
            model = new JSONArrayModel(uri, jsonArray);
        } else if (bracesPattern.matcher(data).matches()) {
            JSONObject jsonObject = new JSONObject(data);
            model = new JSONObjectModel(uri, jsonObject);
        } else {
            Document document = Jsoup.parse(data);
            model = new HtmlModel(uri, document);
        }
        return model;
    }
}
