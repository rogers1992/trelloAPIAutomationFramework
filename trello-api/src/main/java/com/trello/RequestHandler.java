package com.trello;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestHandler {

    private Map<String,String> headers;
    private Map<String, String> queryParams;

    private String baseUrl;

    public RequestHandler(){
        headers = new HashMap<>();
        queryParams = new HashMap<>();
    }
    public void setBaseUrl(String baseUrl){
        this.baseUrl = baseUrl;
    }
    public void setQueryParam(Map<String,String> queryParam){
        this.queryParams.putAll(queryParam);
    }
    public void setHeaders(Map<String, String> headers){
        this.headers.putAll(headers);
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }
    public void setQueryParam(String key, String value){
        this.queryParams.put(key, value);
    }

    public Map<String,?> getHeaders() {
        return this.headers;
    }

    public Map<String,?> getQueryParams() {
        return  this.queryParams;
    }
}
