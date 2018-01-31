package com.qiu.user.controller;

import com.qiu.user.model.BirthName;
import com.qiu.user.model.CnArea;
import com.qiu.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/31 下午4:11
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/list")
    public String getUserList() {
        Page<CnArea> birthNameList = userService.findAllArea();
        System.out.println(birthNameList.getContent());
        System.out.println(birthNameList.getContent().size());
        return "test";
    }
}
