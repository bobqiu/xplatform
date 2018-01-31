package com.qiu.common.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.net.URI;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/31 下午1:33
 */
public class LogUtil {
    public static void  setLogName(String JSName, String configPath){
        String userPath = System.getProperty("user.dir");
        System.out.println(userPath);
        System.setProperty("logFileName", "/Users/bobqiu/data" + "/" + JSName + ".log");//logFileName在log4j2.xml中使用
        System.out.println(System.getProperty("logFileName").toString());

        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);

       /* File filePath = new File(configPath);
        URI configURI = filePath.toURI();
        System.out.println("configURI = " + configURI.toString());//configURI = file:/E:/workspace4J2EE/KMSTool/log4j2.xml
        ctx.setConfigLocation(configURI);*/

       ctx.reconfigure();//如果log4j2.xml在默认路径(src目录)下的情况，就不用获取xml文件的路径了
    }
}
