package com.qiu.weixin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.qiu.utils.HttpXmlClient;
import com.qiu.utils.HttpsUtil;
import com.qiu.weixin.util.UserInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/25 上午10:50
 */
@RestController
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /* public static final String WX_APPID = "wx7387cda711dd08ed";
     public static final String WX_APPSECRET = "9d496795a3b8fc2f7a93f267b509631c";*/
    //private static final  String SCOPE_USERINFO="snsapi_base";snsapi_userinfo
    private static final String SCOPE_USERINFO = "snsapi_userinfo";

    @Value("${appID}")
    private String appId;

    @Value("${appsecret}")
    private String appSecret;

    @Value("${wx_login_callback_url}")
    private String backUrl;

    Hashtable<String, String> hashtable = new Hashtable<>();

    /**
     * 授权登录URL地址<br/>
     *
     * @return String
     * 微信授权的URL路径
     */
    @ResponseBody
    @GetMapping("/login")
    public void authorizedLoginUrl(HttpServletResponse response) throws IOException {
        String redirect_uri = "";

        try {
            logger.info("backUrl:" + backUrl);
            redirect_uri = java.net.URLEncoder.encode(backUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("authorizedLoginWoman>>error>>" + e.getMessage());
        }

        String oauth2Url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=" + redirect_uri
                + "&response_type=code&scope=" + SCOPE_USERINFO + "&state=STATE#wechat_redirect";
        logger.info("authorizedLoginWomanUrl>>" + oauth2Url);
        response.sendRedirect(oauth2Url);
    }

    @GetMapping("/url1") //location
    public ModelAndView wechatLocation(@RequestParam(name = "code", required = false) String code,
                                       @RequestParam(name = "state", required = false) String state) {


        String tokenUrl = UserInfoUtil.getLocToken(appId, appSecret);
        logger.info("get location Access Token URL:{}", tokenUrl);

        // 通过https方式请求获得web_access_token
        String response = HttpsUtil.httpsRequestToString(tokenUrl, "GET", null);

        JSONObject jsonObject = JSON.parseObject(response);

        String webAccessToken = jsonObject.getString("access_token");
        logger.info("webAccessToken:" + webAccessToken);

        String jsApiTicket = UserInfoUtil.getJsApi(webAccessToken);
        String js_ticket = HttpsUtil.httpsRequestToString(jsApiTicket, "GET", null);
        logger.info("js_ticket:{}", js_ticket);

        JSONObject jsTicketJsonObject = JSON.parseObject(js_ticket);

        logger.info("js_ticket_json:{}", jsTicketJsonObject);

        String ticket = jsTicketJsonObject.getString("ticket");
        //String ticket = "hello";
        String noncestr = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        //获取请求url
        // String path = request.getContextPath();
        //以为我配置的菜单是http://yo.bbdfun.com/first_maven_project/，最后是有"/"的，所以url也加上了"/"
        //String url = request.getScheme() + "://" + request.getServerName() +  path + "/";
        String url = "https://scrm.chengjuiot.com/url?code=" + code + "&state=" + state;
        String str = "jsapi_ticket=" + ticket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        //sha1加密
        logger.info("ticket str:" + str);
        String signature = HttpXmlClient.SHA1(str);

        Map<String, String> map = new HashMap<>();
        map.put("signature", signature);
        map.put("timestamp", timestamp);
        map.put("noncestr", noncestr);
        map.put("appid", appId);

        return new ModelAndView("index", map);
    }

    /**
     * 微信网页授权流程:
     * 1. 用户同意授权,获取 code
     * 2. 通过 code 换取网页授权 access_token
     * 3. 使用获取到的 access_token 和 openid 拉取用户信息
     *
     * @param code  用户同意授权后,获取到的code
     * @param state 重定向状态参数
     * @return
     */
    @GetMapping("/url")
    public ModelAndView wecahtLogin(@RequestParam(name = "code", required = false) String code,
                                    @RequestParam(name = "state", required = false) String state) {

        // 1. 用户同意授权,获取code
        logger.info("收到微信重定向跳转.");
        logger.info("用户同意授权,获取code:{} , state:{}", code, state);

        // 2. 通过code换取网页授权access_token
        if (code != null || !(("").equals(code))) {

           /* String APPID = WXAPPID;
            String SECRET = WX_APPSECRET;*/
            String CODE = code;
            String WebAccessToken = "";
            String openId = "";
            String nickName, sex, openid = "";
            String SCOPE = "snsapi_userinfo";

           /* String getCodeUrl = UserInfoUtil.getCode(appId, backUrl, SCOPE);
            logger.info("第一步:用户授权, get Code URL:{}", getCodeUrl);*/

            // 替换字符串，获得请求access token URL
            JSONObject jsonObject = null;
            if (hashtable.get(code) == null) {
                String tokenUrl = UserInfoUtil.getWebAccess(appId, appSecret, CODE);
                logger.info("第二步:get Access Token URL:{}", tokenUrl);

                // 通过https方式请求获得web_access_token
                String response = HttpsUtil.httpsRequestToString(tokenUrl, "GET", null);

                jsonObject = JSON.parseObject(response);
                logger.info("请求到的Access Token:{}", jsonObject.toJSONString());
            } else {
                WebAccessToken = hashtable.get(code).split("\\|")[0];
                openId = hashtable.get(code).split("\\|")[1];

                logger.info("获取access_token成功!");
                logger.info("WebAccessToken:{} , openId:{}", WebAccessToken, openId);

                // 3. 使用获取到的 Access_token 和 openid 拉取用户信息
                String userMessageUrl = UserInfoUtil.getUserMessage(WebAccessToken, openId);
                logger.info("第三步:获取用户信息的URL:{}", userMessageUrl);

                // 通过https方式请求获得用户信息响应
                String userMessageResponse = HttpsUtil.httpsRequestToString(userMessageUrl, "GET", null);

                JSONObject userMessageJsonObject = JSON.parseObject(userMessageResponse);

                logger.info("用户信息:{}", userMessageJsonObject.toJSONString());

                String jsApiTicket = UserInfoUtil.getJsApi(WebAccessToken);
                String js_ticket = HttpsUtil.httpsRequestToString(jsApiTicket, "GET", null);
                logger.info("js_ticket:{}", js_ticket);

                JSONObject jsTicketJsonObject = JSON.parseObject(js_ticket);

                logger.info("js_ticket_json:{}", jsTicketJsonObject);
            }


//            {
//                "access_token":"ACCESS_TOKEN",
//                "expires_in":7200,
//                "refresh_token":"REFRESH_TOKEN",
//                "openid":"OPENID",
//                "scope":"SCOPE",
//                "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
//            }

            if (null != jsonObject) {
                try {

                    if (hashtable.get(code) == null) {
                        WebAccessToken = jsonObject.getString("access_token");
                        openId = jsonObject.getString("openid");
                        String codeValue = WebAccessToken + "|" + openId;
                        hashtable.put(code, codeValue);
                    } else {
                        WebAccessToken = hashtable.get(code).split("\\|")[0];
                        openId = hashtable.get(code).split("\\|")[1];
                    }

                    String jsApiTicket = UserInfoUtil.getJsApi(WebAccessToken);
                    String js_ticket = HttpsUtil.httpsRequestToString(jsApiTicket, "GET", null);
                    logger.info("js_ticket:{}", js_ticket);

                    JSONObject jsTicketJsonObject = JSON.parseObject(js_ticket);

                    logger.info("js_ticket_json:{}", jsTicketJsonObject);


                    logger.info("获取access_token成功!");
                    logger.info("WebAccessToken:{} , openId:{}", WebAccessToken, openId);

                    // 3. 使用获取到的 Access_token 和 openid 拉取用户信息
                    String userMessageUrl = UserInfoUtil.getUserMessage(WebAccessToken, openId);
                    logger.info("第三步:获取用户信息的URL:{}", userMessageUrl);


                    // 通过https方式请求获得用户信息响应
                    String userMessageResponse = HttpsUtil.httpsRequestToString(userMessageUrl, "GET", null);

                    JSONObject userMessageJsonObject = JSON.parseObject(userMessageResponse);


                    logger.info("用户信息:{}", userMessageJsonObject.toJSONString());
//                    {
//                        "openid":" OPENID",
//                        "nickname": NICKNAME,
//                        "sex":"1",
//                        "province":"PROVINCE"
//                        "city":"CITY",
//                        "country":"COUNTRY",
//                        "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MoCfHe/46",
//                        "privilege":[
//                              "PRIVILEGE1"
//                              "PRIVILEGE2"
//                        ],
//                        "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
//                    }

                    if (userMessageJsonObject != null) {
                        try {
                            //用户昵称
                            nickName = userMessageJsonObject.getString("nickname");
                            //用户性别
                            sex = userMessageJsonObject.getString("sex");
                            sex = (sex.equals("1")) ? "男" : "女";
                            //用户唯一标识
                            openid = userMessageJsonObject.getString("openid");

                            logger.info("用户昵称:{}", nickName);
                            logger.info("用户性别:{}", sex);
                            logger.info("OpenId:{}", openid);
                        } catch (JSONException e) {
                            logger.error("获取用户信息失败");
                        }
                    }
                } catch (JSONException e) {
                    logger.error("获取Web Access Token失败");
                }
            }

        } else {

        }

        return new ModelAndView("index");
    }

    @GetMapping("/cookie")
    public ModelAndView cookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("user", "1qaz");
        //cookie.setDomain("https://scrm.chengjuiot.com");
        cookie.setDomain(".chengjuiot.com");
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        Cookie cookie1 = new Cookie("testcookie", "thisisatestcookie");
        cookie1.setDomain(".chengjuiot.com");
        cookie1.setPath("/");
        cookie1.setMaxAge(-1);
        cookie1.setSecure(true);
        cookie1.setHttpOnly(true);
        response.addCookie(cookie1);

        return new ModelAndView("index");
    }

    @GetMapping("/testcookie")
    public ModelAndView testCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        PrintWriter out = response.getWriter();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                out.write(name + "=" + value);
                System.out.println(name + "=" + value);
            }
        }
        return new ModelAndView("test");
    }

    public static void main(String[] args) {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("test", "hello");

        System.out.println(hashtable.get("testa"));
    }


    private String getSigner(String ticket, String code, String state) {

        return null;
    }
}
