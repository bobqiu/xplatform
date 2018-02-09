package com.qiu.authority.service.impl;


import com.qiu.authority.dao.IArticleSortDao;
import com.qiu.authority.dao.support.IBaseDao;
import com.qiu.authority.entity.ArticleSort;
import com.qiu.authority.service.IArticleSortService;
import com.qiu.authority.service.support.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/1 下午3:14
 */
@Service
public class ArticleSortServiceImpl extends BaseServiceImpl<ArticleSort, Integer> implements IArticleSortService {
	@Autowired
	private IArticleSortDao articleSortDao;
	@Override
	public IBaseDao<ArticleSort, Integer> getBaseDao() {
		return this.articleSortDao;
	}

	@Override
	public void saveOrUpdate(ArticleSort article) {
		if(article.getId() != null){
			ArticleSort dbUser = find(article.getId());
			dbUser.setTitle(article.getTitle());
			dbUser.setDescription(article.getDescription());
			dbUser.setUpdateTime(new Date());
			update(dbUser);
		}else{
			article.setCreateTime(new Date());
			article.setUpdateTime(new Date());
			save(article);
		}
	}

	@Override
	public void delete(Integer id) {
		ArticleSort article = find(id);
		//Assert.state(!"admin".equals(article.getTitle()),"超级管理员用户不能删除");
		super.delete(id);
	}



}
