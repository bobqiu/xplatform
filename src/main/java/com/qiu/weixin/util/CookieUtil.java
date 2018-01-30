package com.qiu.weixin.util;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/28 下午10:38
 */
public class CookieUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(CookieUtil.class);
    public static final String COOKIE_KEY = "HtEY%^hRTY5";

    public static void setCookie(String name, String value, int maxAge, boolean httponly, boolean secure,
                                 HttpServletRequest req, HttpServletResponse resp) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(HttpUtil.getCookieDomainFromServerName(req.getServerName()));
        cookie.setPath("/");
        cookie.setMaxAge(maxAge); // 一周
        cookie.setHttpOnly(httponly);
        cookie.setSecure(secure);
        resp.addCookie(cookie);
    }


    public static String getCookie(HttpServletRequest req, String name) {

        String encValue = "";
        try {
            // 从request范围中得到cookie数组 然后遍历放入map集合中
            Cookie[] cookies = req.getCookies();
            if (cookies != null && cookies.length != 0) {

                for (int i = 0; i < cookies.length; ++i) {
                    if (StringUtils.equals(name, cookies[i].getName())) {
                        encValue = cookies[i].getValue();
                        break;
                    }
                }
            }

            LOGGER.info("cookieValue::" + encValue);
            if (StringUtils.isNotBlank(encValue)) {
                String decValue = DesBase64Utils.decrypt(encValue, COOKIE_KEY);
                if (StringUtils.isNotBlank(decValue)) {
                    String[] segs = decValue.split("[|]");
                    if (segs.length < 6) {
                        return null;
                    }
                    String base = new StringBuffer(segs[0]).append("|").append(segs[1]).append("|").append(segs[2])
                            .append("|").append(segs[3]).append("|").append(segs[4]).toString();

                    String sign = DigestUtils.sha256Hex(base).substring(3, 15);
                    if (!StringUtils.equals(sign, segs[5])) {
                        return null;
                    }

                    return decValue;
                }
                return null;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getCookieValue(HttpServletRequest req, String name) {

        String encValue = "";
        try {
            // 从request范围中得到cookie数组 然后遍历放入map集合中
            Cookie[] cookies = req.getCookies();
            if (cookies != null && cookies.length != 0) {

                for (int i = 0; i < cookies.length; ++i) {
                    if (StringUtils.equals(name, cookies[i].getName())) {
                        encValue = cookies[i].getValue();
                        break;
                    }
                }
            }

            //log.info("cookieValue::" + encValue);
            return encValue;
        } catch (Exception ex) {
            return null;
        }
    }


}
