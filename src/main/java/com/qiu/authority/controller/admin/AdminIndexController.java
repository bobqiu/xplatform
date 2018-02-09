package com.qiu.authority.controller.admin;

import com.qiu.authority.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/1 下午3:14
 */

@Controller
public class AdminIndexController extends BaseController {
	@RequestMapping(value ={"/admin/","/admin/index"})
	public String index(){

		return "admin/index";
	}

	@RequestMapping(value = {"/admin/welcome"})
	public String welcome(){

		return "admin/welcome";
	}
}
