package com.qiu.authority.controller.admin.system;



import com.qiu.authority.common.JsonResult;
import com.qiu.authority.controller.BaseController;
import com.qiu.authority.entity.Role;
import com.qiu.authority.service.IResourceService;
import com.qiu.authority.service.IRoleService;
import com.qiu.authority.service.specification.SimpleSpecificationBuilder;
import com.qiu.authority.service.specification.SpecificationOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/1 下午3:14
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IResourceService resourceService;

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "admin/role/index";
	}

	@RequestMapping(value = { "/list" })
	@ResponseBody
	public Page<Role> list() {
		SimpleSpecificationBuilder<Role> builder = new SimpleSpecificationBuilder<Role>();
		String searchText = request.getParameter("searchText");
		if(StringUtils.isNotBlank(searchText)){
			builder.add("name", SpecificationOperator.Operator.likeAll.name(), searchText);
			builder.addOr("description", SpecificationOperator.Operator.likeAll.name(), searchText);
		}
		Page<Role> page = roleService.findAll(builder.generateSpecification(), getPageRequest());
		return page;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap map) {
		return "admin/role/form";
	}


	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id,ModelMap map) {
		Role role = roleService.find(id);
		map.put("role", role);
		return "admin/role/form";
	}


	@RequestMapping(value= {"/edit"},method = RequestMethod.POST)
	@ResponseBody
	public JsonResult edit(Role role, ModelMap map){
		try {
			roleService.saveOrUpdate(role);
		} catch (Exception e) {
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(@PathVariable Integer id,ModelMap map) {
		try {
			roleService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}

	@RequestMapping(value = "/grant/{id}", method = RequestMethod.GET)
	public String grant(@PathVariable Integer id, ModelMap map) {
		Role role = roleService.find(id);
		map.put("role", role);
		return "admin/role/grant";
	}

	@RequestMapping(value = "/grant/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult grant(@PathVariable Integer id,
			@RequestParam(required = false) String[] resourceIds, ModelMap map) {
		try {
			roleService.grant(id,resourceIds);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
}
