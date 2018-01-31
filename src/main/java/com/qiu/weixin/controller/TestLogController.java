package com.qiu.weixin.controller;


import com.qiu.common.log.LogUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/30 下午9:40
 */
@Controller
public class TestLogController {

    private static final Logger LOGGER = LogManager.getLogger(TestLogController.class);

    private static final Logger aLogger = LogManager.getLogger("appender_business-a-msg");

    private static final Logger bLogger = LogManager.getLogger("appender_business-b-msg");
    private static final Logger dLogger = LogManager.getLogger("dynamic_log");


    @GetMapping("/testlog")
    public void test() {
        aLogger.info("hits a test log {} {}", "aa", new Date());
        bLogger.info("hits a test log {} {}", "bb", new Date());

        LogUtil.setLogName("hello", "");
        dLogger.info("thits a dynamic  log {} {}", "aa", new Date());

    }
}
