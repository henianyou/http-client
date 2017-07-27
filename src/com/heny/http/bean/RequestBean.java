package com.heny.http.bean;

import java.util.HashMap;
import java.util.Map;

public class RequestBean{

    private String url;
    private Map<String, String> header = new HashMap<>();
    private Object params;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }
}
