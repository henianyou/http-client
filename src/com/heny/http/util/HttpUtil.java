package com.heny.http.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.heny.http.bean.HttpResEntity;
import com.heny.http.bean.RequestBean;

/**
 * 
********************************************************** 
* @作者: Administrator
* @日期: 2015年9月11日
* @版权: 2015 www.shuzun.net Inc. All rights reserved.
* @描述: 
**********************************************************
 */
public class HttpUtil {

    public static final String URL = "http://139.196.149.178:8090/http-test/request";

    /**
     * 使用Get方式获取数据
     * @param url url
     * @param paramMap 参数（如果url中已包含参数则可以为null）
     * @param header 消息头（可以为null）
     * @return
     */
    public static HttpResEntity sendGet(String url, Map<String, String> paramMap,
            Map<String, String> header) {

        HttpResEntity resEntity = new HttpResEntity();
        resEntity.setResCode(404); // 默认返回404错误
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            if (null != paramMap && !paramMap.isEmpty()) {
                List<NameValuePair> list = new ArrayList<>(paramMap.size());
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(list, "UTF-8"));
            }
            client = HttpConnectionManager.getHttpClient();
            HttpGet httpGet = new HttpGet(url);

            // 设置消息头
            if (null != header && !header.isEmpty()) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }

            response = client.execute(httpGet);
            resEntity = getResult(response);
            httpGet.releaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            relase(response);
        }
        return resEntity;
    }

    public static HttpResEntity sendGetByProxy(String url, Map<String, String> paramMap,
            Map<String, String> header) {

        RequestBean bean = new RequestBean();
        bean.setUrl(url);
        bean.setParams(paramMap);
        bean.setHeader(header);
        if (url.startsWith("https://")) {
            return sendPostJson(URL + "/https/get", JSON.toJSONString(bean), null);
        } else {
            return sendPostJson(URL + "/http/get", JSON.toJSONString(bean), null);
        }
    }

    /**
     * POST请求，json形式数据
     * @param url 请求地址
     * @param param 请求数据json格式
     * @param header 消息头 没有可为null
     */
    public static HttpResEntity sendPostJson(String url, String param, Map<String, String> header) {

        HttpResEntity resEntity = new HttpResEntity();
        resEntity.setResCode(404); // 默认返回404错误

        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = HttpConnectionManager.getHttpClient();
            HttpPost httpPost = new HttpPost(url);

            // 设置消息头
            if (null != header && !header.isEmpty()) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }

            // 设置参数
            StringEntity stringEntity = new StringEntity(param, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);

            response = client.execute(httpPost);
            resEntity = getResult(response);
            httpPost.releaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            relase(response);
        }

        return resEntity;
    }

    public static HttpResEntity sendPostJsonByProxy(String url, String param,
            Map<String, String> header) {

        RequestBean bean = new RequestBean();
        bean.setUrl(url);
        bean.setParams(param);
        bean.setHeader(header);
        if (url.startsWith("https://")) {
            return sendPostJson(URL + "/https/post", JSON.toJSONString(bean), null);
        } else {
            return sendPostJson(URL + "/http/post", JSON.toJSONString(bean), null);
        }
    }

    /**
     * POST请求，Map形式数据
     * @param url 请求地址
     * @param param 请求数据
     * @param charset 编码方式
     */
    public static HttpResEntity sendPost(String url, Map<String, String> param,
            Map<String, String> header) {

        HttpResEntity resEntity = new HttpResEntity();
        resEntity.setResCode(404); // 默认返回404错误

        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = HttpConnectionManager.getHttpClient();
            HttpPost httpPost = new HttpPost(url);

            // 设置消息头
            if (null != header && !header.isEmpty()) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }

            // 设置参数
            if (null != param && !param.isEmpty()) {
                List<NameValuePair> list = new ArrayList<>();
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    NameValuePair valuePair =
                            new BasicNameValuePair(entry.getKey(), entry.getValue());
                    list.add(valuePair);
                }
                if (!list.isEmpty()) {
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
                    httpPost.setEntity(entity);
                }
            }

            response = client.execute(httpPost);
            resEntity = getResult(response);
            httpPost.releaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            relase(response);
        }
        return resEntity;
    }

    public static HttpResEntity sendPostByProxy(String url, Map<String, String> param,
            Map<String, String> header) {
        RequestBean bean = new RequestBean();
        bean.setUrl(url);
        bean.setParams(param);
        bean.setHeader(header);
        if (url.startsWith("https://")) {
            return sendPostJson(URL + "/https/post", JSON.toJSONString(bean), null);
        } else {
            return sendPostJson(URL + "/http/post", JSON.toJSONString(bean), null);
        }
    }

    /**
     * 获取结果
     * @param response
     * @return
     * @throws ParseException
     * @throws IOException
     */
    private static HttpResEntity getResult(CloseableHttpResponse response)
            throws ParseException, IOException {
        HttpResEntity resEntity = new HttpResEntity();
        if (response != null) {
            resEntity.setResCode(response.getStatusLine().getStatusCode());
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                String resp = EntityUtils.toString(httpEntity, "UTF-8");
                resEntity.setResContent(resp);
            }
        }
        return resEntity;
    }

    /**
     * 释放连接
     * @param response
     */
    private static void relase(CloseableHttpResponse response) {
        if (response != null) {
            EntityUtils.consumeQuietly(response.getEntity());
        }
    }
}
