package com.qiu.authority.service;


import com.qiu.authority.entity.Article;
import com.qiu.authority.service.support.IBaseService;
import com.qiu.authority.vo.Tags;

import java.util.List;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/1 下午3:14
 */
public interface IArticleService extends IBaseService<Article, Integer> {

	/**
	 * 增加或者修改文章
	 * @param article
	 */
	void saveOrUpdate(Article article);

    void delete(Integer id);
	List<Article> findBySortName(String sortName);
	List<Tags> findTags(String tag);
	List<Article> findAllByLabel(String label);
}
