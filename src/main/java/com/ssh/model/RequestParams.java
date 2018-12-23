package com.ssh.model;

/**
 * 爬重爬取时所需要的请求参数
 */
public class RequestParams implements Cloneable{
    private String target;//目标地址
    private String cookie;//cookie
    private String referer;//referer
    private String charset;//charset
    private String userAgent;//userAgent
    private String childUrlHead;//目标地址的子地址的头部
    private String childUrlTail;//目标地址的子地址的尾部

    public RequestParams(){

    }
    public RequestParams(String target, String cookie, String referer, String charset, String userAgent, String childUrlHead, String childUrlTail) {
        this.target = target;
        this.cookie = cookie;
        this.referer = referer;
        this.charset = charset;
        this.userAgent = userAgent;
        this.childUrlHead = childUrlHead;
        this.childUrlTail = childUrlTail;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getChildUrlHead() {
        return childUrlHead;
    }

    public void setChildUrlHead(String childUrlHead) {
        this.childUrlHead = childUrlHead;
    }

    public String getChildUrlTail() {
        return childUrlTail;
    }

    public void setChildUrlTail(String childUrlTail) {
        this.childUrlTail = childUrlTail;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
