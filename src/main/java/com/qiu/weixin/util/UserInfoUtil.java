package com.qiu.weixin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/25 上午10:46
 */
public class UserInfoUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 1.获取code的请求地址
    public static String Get_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STAT#wechat_redirect";
    // 替换字符串
    public static String getCode(String APPID, String REDIRECT_URI,String SCOPE) {
        return String.format(Get_Code,APPID,REDIRECT_URI,SCOPE);
    }

    // 2.获取Web_access_tokenhttps的请求地址
    public static String Web_access_tokenhttps = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    // 替换字符串
    public static String getWebAccess(String APPID, String SECRET,String CODE) {
        return String.format(Web_access_tokenhttps, APPID, SECRET,CODE);
    }

    // 3.拉取用户信息的请求地址
    public static String User_Message = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
    // 替换字符串
    public static String getUserMessage(String access_token, String openid) {
        return String.format(User_Message, access_token,openid);
    }

    //获取用户位置信息
    public static String User_Location = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
    public static String getJsApi(String access_token) {
        return String.format(User_Location, access_token);
    }

    public static String user_location_token="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appId=%s&secret=%s";
    public static String getLocToken(String appId,String secret) {
        return String.format(user_location_token, appId,secret);
    }

    public static void main(String[] args) {
        String REDIRECT_URI = "http://wechat.tmqyt.com/url";
        String SCOPE = "snsapi_login"; // snsapi_userinfo // snsapi_login

        //appId
        String appId = "wx222e322a20897ea3";

        String getCodeUrl = getCode(appId, REDIRECT_URI, SCOPE);
        System.out.println("getCodeUrl:"+getCodeUrl);
    }
}
