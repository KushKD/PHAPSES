package com.doi.himachal.Modal;

import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 05, 07 , 2020
 */
public class ResponsePojoGet implements Serializable {

    private String url;
    private String requestParams;
    private String response;
    private String functionName;
    private String params;
    private String responseCode;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ResponsePojoGet{" +
                "url='" + url + '\'' +
                ", requestParams='" + requestParams + '\'' +
                ", response='" + response + '\'' +
                ", functionName='" + functionName + '\'' +
                ", params='" + params + '\'' +
                ", responseCode='" + responseCode + '\'' +
                '}';
    }
}