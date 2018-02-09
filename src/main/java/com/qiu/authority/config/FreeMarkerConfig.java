package com.qiu.authority.config;

import javax.annotation.PostConstruct;


import com.qiu.authority.config.shiro.freemarker.ShiroTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/1 下午3:14
 */
@Configuration
public class FreeMarkerConfig {

    @Autowired
    private freemarker.template.Configuration configuration;

    @PostConstruct
    public void setSharedVariable() {
    	try {
			configuration.setSharedVariable("shiro", new ShiroTags());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
