package com.qiu.weixin.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/28 下午10:52
 */
public class HttpUtil {
    /**
     *
     */

    static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static String getRemortIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isNotBlank(ip)) {
            ip = ip.split(",")[0];
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip.trim();
    }


    public static String getProxyIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isNotBlank(ip)) {
            String[] ipsegs = ip.split(",");
            ip = ipsegs[ipsegs.length - 1];
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip.trim();
    }

    public static String getParamValues(HttpServletRequest req) {

        Map params = req.getParameterMap();
        StringBuffer sbParams = new StringBuffer();
        if (!(params.isEmpty())) {
            sbParams.append("?");
            for (Object key : params.keySet()) {
                Object obj = params.get(key);
                String val = "";
                if (obj instanceof String[]) {
                    String[] strs = (String[]) obj;
                    for (int i = 0; i < strs.length; i++) {
                        val = strs[i] + ",";
                    }
                    val = val.substring(0, val.length() - 1);

                } else {
                    val = obj.toString();
                }
                sbParams.append(key + "=" + val).append("&");
            }
        }
        return sbParams.toString();
    }

    /**
     * 后端slb和ecs之间只能走http，request.getScheme()获取的也始终是http
     *
     * @param request
     * @return
     */
    public static String getScheme(HttpServletRequest request) {
        // String scheme = request.getScheme();
        // StringBuffer sb = request.getRequestURL();
        // if (StringUtils.isNotBlank(sb)) {
        // String url = sb.toString();
        // if (url.startsWith("https") || url.startsWith("HTTPS")) {
        // return "https";
        // }
        // }

        String protocol = System.getProperty("system.protocol");
        if (StringUtils.isBlank(protocol)) {
            protocol = request.getScheme();
        }

        return protocol;
    }

    public static String getRequestFullUrl(HttpServletRequest request) {
        // String protocol = System.getProperty("system.protocol");
        // if (StringUtils.isBlank(protocol)) {
        // protocol = request.getScheme();
        // }
        String protocol = getScheme(request);

        String serverName = request.getServerName();
        if (StringUtils.equals("dev", System.getProperty("system.condition"))) {
            serverName += ":" + request.getServerPort();
        }
        String uri = request.getRequestURI();
        String requestUrl = protocol + "://" + serverName + uri;
        String queryString = request.getQueryString();
        if (queryString != null) {
            requestUrl = requestUrl + "?" + queryString;
        }
        return requestUrl;
    }

    public static String getCookieDomainFromServerName(String serverName) {
        if (StringUtils.isBlank(serverName)) {
            return System.getProperty("cookie.domain");
        }
        String[] segs = serverName.split("[.]");
        return new StringBuffer().append(segs[segs.length - 2]).append(".").append(segs[segs.length - 1]).toString();
    }

    public static String getRequestUrl(HttpServletRequest request) {
        String protocol = System.getProperty("system.protocol");
        if (StringUtils.isBlank(protocol)) {
            protocol = request.getScheme();
        }
        String serverName = request.getServerName();
        if (StringUtils.equals("dev", System.getProperty("system.condition"))) {
            serverName += ":" + request.getServerPort();
        }
        String uri = request.getRequestURI();
        String requestUrl = protocol + "://" + serverName + uri;
        return requestUrl;
    }

    public static String getDomain(HttpServletRequest request) {
        String protocol = System.getProperty("system.protocol");
        if (StringUtils.isBlank(protocol)) {
            protocol = request.getScheme();
        }
        String serverName = request.getServerName();
        if (StringUtils.equals("dev", System.getProperty("system.condition"))) {
            serverName += ":" + request.getServerPort();
        }
        String domain = protocol + "://" + serverName;
        return domain;
    }

    public static String getRequestFullUrlWithoutProperties(HttpServletRequest request) {
        String protocol = request.getScheme();
        String serverName = request.getServerName();
        if (StringUtils.equals("dev", System.getProperty("system.condition"))) {
            serverName += ":" + request.getServerPort();
        }
        String uri = request.getRequestURI();
        String requestUrl = protocol + "://" + serverName + uri;
        String queryString = request.getQueryString();
        if (queryString != null) {
            requestUrl = requestUrl + "?" + queryString;
        }
        return requestUrl;
    }

    public static Map<String, String> parseParams(String bodys) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<String, String>();
        String[] s1 = bodys.split("&");
        for (String s : s1) {
            if (StringUtils.isNotBlank(s)) {
                String[] s2 = s.split("=");
                map.put(s2[0], URLDecoder.decode(s2[1], "UTF-8"));
            }
        }
        return map;
    }

    public static Object getJsonResult(Map<String, Object> dataInner, Map<String, Object> error) {
        if (dataInner == null) {
            dataInner = new HashMap<String, Object>();
        }
        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("error", error);
        data.put("data", dataInner);
        res.put("body", data);
        return res;
    }

    public static void processHeaders(HttpServletRequest request, HttpServletResponse response) {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("version", "1.0");
        headers.put("messengerid", "" + System.currentTimeMillis());
        headers.put("timestamp", "" + System.currentTimeMillis());
        headers.put("digest", DigestUtils.md5Hex("" + System.currentTimeMillis()));

        Object m = request.getAttribute("_mobile");
        Object nick = request.getAttribute("_user_nickname");
        Object avatar = request.getAttribute("_user_avatar");

        if (nick != null) {
            String nickName = nick.toString();
            // log.info("user header nickname: " + nickName);
            headers.put("nickname", nickName);
        } else {

            if (m != null) {
                log.info("user header mobile: " + m.toString());
                String mobile = m.toString();
                String enc = mobile.substring(0, 3) + "****" + mobile.substring(7);
                headers.put("nickname", enc);
            } else {
                headers.put("nickname", "***");
            }
        }

        if (avatar != null) {
            headers.put("avatar", avatar.toString());
        }

        // log.info(String.valueOf(response == null) + "|" +
        // String.valueOf(headers == null));
        response.addHeader("headers", JsonUtil.writeValueAsString(headers));
        response.setHeader("Access-Control-Allow-Origin", "*:*");
    }

    public static Map<String, Cookie> toMap(Cookie[] cookies) {
        if (cookies == null || cookies.length <= 0) {
            return Collections.emptyMap();
        }

        Map<String, Cookie> map = new HashMap<String, Cookie>();
        for (Cookie c : cookies) {
            map.put(c.getName(), c);
        }
        return map;
    }

    public static void main(String[] args) {
        System.out.println(getCookieDomainFromServerName("taozuicdn.taiheiot.cn"));
    }

}


