package com.cq.base.http.huto;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;

public class T_01_Huto {

    public static void main(String[] args) {
        HttpResponse execute = HttpUtil.createRequest(Method.GET, "www.baidu.com")
                .header("Content-Type", "application/vnd.schemaregistry.v1+json")
                .execute();
        System.out.println(execute.body());
    }
}
