package com.offcn.common.test;

import com.offcn.common.utils.HttpUtils;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class SmsTest {
    public static void main(String[] args) throws Exception {

        String host = "http://dingxin.market.alicloudapi.com";
        String path = "/dx/sendSms";
        String method = "POST";
        String AppCode = "APPCODE c5e745d2b72b44439bbda51e105eab6e";

        // 请求头
        Map<String,String> header = new HashMap();
        header.put("Authorization",AppCode);
        header.put("Content-Type","application/x-www-form-urlencoded");

        // 请求参数
        Map<String,String> query = new HashMap<String, String>();
        query.put("mobile","15637691752");
        query.put("param","code:888918");
        query.put("tpl_id","TP1711063");

        HttpResponse httpResponse = HttpUtils.doPost(host, path, method, header, query, "");
        System.out.println(httpResponse.toString());

    }
}
