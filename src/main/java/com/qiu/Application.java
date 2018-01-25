package com.qiu;

import com.qiu.servlet.ServerPortal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/24 下午5:10
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public ServletRegistrationBean testServletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new ServerPortal());

        registrationBean.addUrlMappings("/protal");
        return registrationBean;
    }
}
