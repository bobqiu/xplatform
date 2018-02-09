package com.qiu.authority.controller.web;

import com.qiu.authority.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/1 下午3:14
 */
@Controller
public class IndexController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 系统首页
     * /和/index都可以访问此方法
     * author:贤云
     *
     * @return
     */
    @RequestMapping(value = {"/"})
    public String index() {
        logger.debug("进入首页");
        return "index";
    }
}
