package com.qiu.weixin.controller;


import com.qiu.common.log.LogUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/30 下午9:40
 *  <RollingFile name="dynamic_log" fileName= "${sys:logFileName}"
 *filePattern="${sys:logFileName}-%i.log">
 *<ThresholdFilter level="DEBUG" onMatch="ACCEPT" onMismatch="DENY"/>
 *<PatternLayout pattern="[%-5level][%d{yyyy-MM-dd HH:mm:ss}][%F:%L] - %m%n" />
 *
 *<SizeBasedTriggeringPolicy size="100MB" />
 *<DefaultRolloverStrategy max="20"/>
 *</RollingFile>
 * <loggers>
 *<AsyncLogger name="appender_system-msg" level="info" additivity="true"  includeLocation="true" >
 *<AppenderRef ref="appender_system-msg" />
 *</AsyncLogger>
 *<AsyncLogger name="appender_business-a-msg" level="info" additivity="true"  includeLocation="true">
 *<AppenderRef ref="appender_business-a-msg" />
 *</AsyncLogger>
 *<AsyncLogger name="appender_business-b-msg" level="info" additivity="true"  includeLocation="true">
 *<AppenderRef ref="appender_business-b-msg" />
 *</AsyncLogger>
 *<logger name="dynamic_log">
 *<AppenderRef ref="dynamic_log" />
 *</logger>
 *<Root level="info">
 *<AppenderRef ref="console" />
 *<AppenderRef ref="appender_system-msg"/>
 *</Root>
 *</loggers>
 */
@Controller
public class TestLogController {

    private static final Logger LOGGER = LogManager.getLogger(TestLogController.class);

    private static final Logger aLogger = LogManager.getLogger("appender_business-a-msg");

    private static final Logger bLogger = LogManager.getLogger("appender_business-b-msg");
    private static final Logger dLogger = LogManager.getLogger("dynamic_log");


    @GetMapping("/testlog")
    public String test(HttpServletRequest request) {
        String log = request.getParameter("i");
        aLogger.info("hits a test log {} {}", "aa", new Date());
        bLogger.info("hits a test log {} {}", "bb", new Date());

        LogUtil.setLogName("hello1"+log, "");
        dLogger.info("thits a dynamic  log {} {}", "aa", new Date());
        return "test";
    }
}
