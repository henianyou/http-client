package com.heny.http.bean;

import java.net.HttpURLConnection;

/**
 * http请求返回的实体
* @Description 
* @author hny
* @date 2016年7月13日 下午5:41:16 
* @version V1.0
* @since jdk1.7
 */
public class HttpResEntity {
    /**
     * http请求返回的状态码 如：请求成功返回 200
     */
    private int resCode;
    /**
     * http请求返回的内容
     */
    private String resContent;

    public HttpResEntity() {
        this.setResCode(HttpURLConnection.HTTP_NOT_FOUND);
        this.setResContent("");
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResContent() {
        return resContent;
    }

    public void setResContent(String resContent) {
        this.resContent = resContent;
    }

}
