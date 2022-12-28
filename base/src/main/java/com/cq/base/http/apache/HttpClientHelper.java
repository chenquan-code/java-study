package com.cq.base.http.apache;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class HttpClientHelper {
    public static String sendPost(String urlParam, Map<String, String> headerMap) throws HttpException, IOException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建post请求方法实例对象
        PostMethod postMethod = new PostMethod(urlParam);
        // 设置post请求超时时间
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);

        wrapPostHeader(headerMap, postMethod);

        httpClient.executeMethod(postMethod);

        String result = postMethod.getResponseBodyAsString();
        postMethod.releaseConnection();
        return result;
    }

    public static String sendPostByJson(String urlParam, String json) throws IOException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建post请求方法实例对象
        PostMethod postMethod = new PostMethod(urlParam);
        // 设置post请求超时时间
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            StringRequestEntity entity = new StringRequestEntity(json, "TEXT/JSON", "UTF-8");
            postMethod.setRequestEntity(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpClient.executeMethod(postMethod);
        String result = postMethod.getResponseBodyAsString();
        postMethod.releaseConnection();
        return result;
    }

    public static String sendGet(String urlParam, Map<String, String> headerMap) throws HttpException, IOException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建GET请求方法实例对象
        GetMethod getMethod = new GetMethod(urlParam);
        // 设置post请求超时时间
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        wrapGetHeader(headerMap, getMethod);
        httpClient.executeMethod(getMethod);
        String result = getMethod.getResponseBodyAsString();
        getMethod.releaseConnection();
        return result;
    }

    /**
     * 处理 post
     *
     * @param headerMap
     * @param postMethod
     */
    private static void wrapPostHeader(Map<String, String> headerMap, PostMethod postMethod) {
        if (null != headerMap) {
            for (Map.Entry<String, String> headerEntry : headerMap.entrySet()) {
                postMethod.addRequestHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }
    }


    /**
     * 处理 get
     *
     * @param headerMap
     * @param getMethod
     */
    private static void wrapGetHeader(Map<String, String> headerMap, GetMethod getMethod) {
        if (null != headerMap) {
            for (Map.Entry<String, String> headerEntry : headerMap.entrySet()) {
                getMethod.addRequestHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(1/2);
    }

}
