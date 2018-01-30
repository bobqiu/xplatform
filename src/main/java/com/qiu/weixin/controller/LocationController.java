package com.qiu.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.qiu.utils.HttpXmlClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/26 上午11:11
 */
@Controller
public class LocationController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/loc")
    public ModelAndView getIndex(HttpServletRequest request){

        ModelAndView mav = new ModelAndView("index");
        //获取access_token
        Map<String, String> params = new HashMap<String, String>();
        params.put("corpid","wx7099477f2de8aded");
        params.put("corpsecret","4clWzENvHVmpcyuA4toys0URkfYanIqWtxZ5plbisn6Cd5AVTF0thpaK6UAhjIvN");
        String xml = HttpXmlClient.post("https://qyapi.weixin.qq.com/cgi-bin/gettoken",params);
       /* JSONObject jsonMap  = JSONObject.fromObject(xml);
        Map<String, String> map = new HashMap<String, String>();
        Iterator<String> it = jsonMap.keys();
        while(it.hasNext()) {
            String key = (String) it.next();
            String u = jsonMap.get(key).toString();
            map.put(key, u);
        }
        String access_token = map.get("access_token");

        //获取ticket
        params.put("access_token",access_token);
        xml = HttpXmlClient.post("https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket",params);
        *//*jsonMap  = JSONObject.fromObject(xml);
        map = new HashMap<String, String>();
        it = jsonMap.keys();
        while(it.hasNext()) {
            String key = (String) it.next();
            String u = jsonMap.get(key).toString();
            map.put(key, u);
        }*//*
        String jsapi_ticket = map.get("ticket");


        //获取签名signature
        String noncestr = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        //获取请求url
        String path = request.getContextPath();
        //以为我配置的菜单是http://yo.bbdfun.com/first_maven_project/，最后是有"/"的，所以url也加上了"/"
        String url = request.getScheme() + "://" + request.getServerName() +  path + "/";
        String str = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        //sha1加密
        String signature = HttpXmlClient.SHA1(str);
        mav.addObject("signature", signature);
        mav.addObject("timestamp", timestamp);
        mav.addObject("noncestr", noncestr);
        mav.addObject("appId", "wx7099477f2de8aded");
        System.out.println("jsapi_ticket=" + jsapi_ticket);
        System.out.println("noncestr=" + noncestr);
        System.out.println("timestamp=" + timestamp);
        System.out.println("url=" + url);
        System.out.println("str=" + str);
        System.out.println("signature=" + signature);*/
        return mav;

    }

}
