package com.qiu.user.controller;

import com.qiu.common.exception.GlobalException;
import com.qiu.common.exception.ParamException;
import com.qiu.common.exception.ResponseData;
import com.qiu.common.exception.ServerException;
import com.qiu.user.model.CnArea;
import com.qiu.user.service.UsersService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/31 下午4:11
 */
@Controller
public class UsersController {


    @Autowired
    private UsersService userService;

    @GetMapping("/user/list")
    public String getUserList() {
        Page<CnArea> birthNameList = userService.findAllArea();
        System.out.println(birthNameList.getContent());
        System.out.println(birthNameList.getContent().size());
        for (CnArea cnArea : birthNameList.getContent()
                ) {
            System.out.println(cnArea);
        }
        return "test";
    }

    @RequestMapping("/students")
    Object queryStudents() throws GlobalException {
        return ResponseData.ok(userService.findAll());
    }

    @ResponseBody
    @RequestMapping("/students/{name}")
    Object queryStudentByName(@PathVariable String name) throws Exception {
        if(name.equals("1")){
            throw new ParamException("参数错误");
        }
        if(name.equals("2")){
            throw new ServerException("内部错误");
        }
        Page<CnArea> allArea = userService.findAllArea();
        return ResponseData.ok(allArea);
    }
}
