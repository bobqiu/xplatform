package com.qiu.authority.dao;

import com.qiu.authority.dao.support.IBaseDao;
import com.qiu.authority.entity.Article;
import org.springframework.stereotype.Repository;

@Repository
public interface IArticleDao extends IBaseDao<Article, Integer> {
    java.util.List<Article> findBySortName(String sortName);

}
