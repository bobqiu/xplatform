package com.qiu.weixin.controller;


import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/testlog")
    public void test() {
        aLogger.info("hits a test log {}","aa");
        bLogger.info("hits a test log {}","bb");
    }
}
