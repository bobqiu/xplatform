package com.qiu.authority.controller.admin.article;


import com.qiu.authority.common.JsonResult;
import com.qiu.authority.controller.BaseController;
import com.qiu.authority.entity.ArticleSort;
import com.qiu.authority.service.IArticleSortService;
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
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/1 下午3:14
 */

@Controller
@RequestMapping("/admin/article/sort")
public class ArticleSortController extends BaseController {
	@Autowired
	private IArticleSortService articleSortService;
	/**
	 * @deprecated 初始化访问页面
	 * @author 贤云
	 * @qq 799078779
	 * @return
	 */
	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "admin/article/sort/index";
	}

	/**
	 *@deprecated  获取json数据集
	 * @author 贤云
	 * @qq 799078779
	 * @return
	 */
	@RequestMapping(value = { "/list" })
	@ResponseBody
	public Page<ArticleSort> list() {
		Page<ArticleSort> page = null;
		try{
			SimpleSpecificationBuilder<ArticleSort> builder = new SimpleSpecificationBuilder<ArticleSort>();
			String searchText = request.getParameter("searchText");
			if(StringUtils.isNotBlank(searchText)){
				builder.add("nickName", SpecificationOperator.Operator.likeAll.name(), searchText);
			}
			page=articleSortService.findAll(builder.generateSpecification(), getPageRequest());

		}catch (Exception e){
			e.printStackTrace();
		}

		return page;
	}

	/**
	 *@deprecated  新增页面初始化
	 * @author 贤云
	 * @qq 799078779
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap map) {
		return "admin/article/sort/form";
	}

	/**
	 *@deprecated  编辑页面初始化
	 * @author 贤云
	 * @qq 799078779
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id,ModelMap map) {
		ArticleSort article = articleSortService.find(id);
		map.put("articleSort", article);
		return "admin/article/sort/form";
	}

	/**
	 *@deprecated  新增或者编辑文章保存
	 * @author 贤云
	 * @qq 799078779
	 * @param map
	 * @return
	 */
	@RequestMapping(value= {"/edit"} ,method = RequestMethod.POST)
	@ResponseBody
	public JsonResult edit(ArticleSort article, ModelMap map){
		try {
			articleSortService.saveOrUpdate(article);
		} catch (Exception e) {
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}

	/**
	 *@deprecated  根据文章id删除文章信息
	 * @author 贤云
	 * @qq 799078779
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(@PathVariable Integer id,ModelMap map) {
		try {
			articleSortService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}

}
