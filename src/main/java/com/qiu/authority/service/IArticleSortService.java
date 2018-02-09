package com.qiu.authority.service;


import com.qiu.authority.entity.ArticleSort;
import com.qiu.authority.service.support.IBaseService;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/1 下午3:14
 */
public interface IArticleSortService extends IBaseService<ArticleSort, Integer> {

	/**
	 * 增加或者修改文章分类
	 * @param article
	 */
	void saveOrUpdate(ArticleSort article);

    void delete(Integer id);

}
