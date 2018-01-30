package com.qiu.common.dao.impl;

import com.qiu.common.dao.BaseJpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/29 下午11:23
 */
@Repository
public class BaseJpaDaoImpl<T, ID extends Serializable> implements BaseJpaDao<T, ID> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseJpaDao.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean save(T entity) {
        boolean flag = false;
        try {
            entityManager.persist(entity);
            flag = true;
        } catch (Exception e) {
            LOGGER.info("--保存出错-{}",entity.toString());
            throw e;
        }
        return flag;
    }

    @Transactional
    @Override
    public Object findByid(Object o, Long id) {
        return entityManager.find(o.getClass(), id);
    }

    @Transactional
    @Override
    public List<T> findBysql(String tablename, String filed, Object o) {
        String sql = "from " + tablename + " u WHERE u." + filed + "=?";
        LOGGER.debug("-sql语句-{}",sql);
        Query query = entityManager.createQuery(sql);
        query.setParameter(1, o);
        List<T> list = query.getResultList();
        entityManager.close();
        return list;
    }

    @Override
    public Object findObjiectBysql(String tablename, String filed, Object o) {
        String sql = "from " + tablename + " u WHERE u." + filed + "=?";
        LOGGER.info(sql + "--------sql语句-------------");
        Query query = entityManager.createQuery(sql);
        query.setParameter(1, o);

        entityManager.close();
        return query.getSingleResult();
    }

    @Transactional
    @Override
    public List<T> findByMoreFiled(String tablename, LinkedHashMap<String, Object> map) {
        String sql = "from " + tablename + " u WHERE ";
        Set<String> set = null;
        set = map.keySet();
        List<String> list = new ArrayList<>(set);
        List<Object> filedlist = new ArrayList<>();
        for (String filed : list) {
            sql += "u." + filed + "=? and ";
            filedlist.add(filed);
        }
        sql = sql.substring(0, sql.length() - 4);
        LOGGER.info(sql + "--------sql语句-------------");
        Query query = entityManager.createQuery(sql);
        for (int i = 0; i < filedlist.size(); i++) {
            query.setParameter(i + 1, map.get(filedlist.get(i)));
        }
        List<T> listRe = query.getResultList();
        entityManager.close();
        return listRe;
    }

    @Transactional
    @Override
    public List<T> findByMoreFiledpages(String tablename, LinkedHashMap<String, Object> map, int start, int pageNumber) {
        String sql = "from " + tablename + " u WHERE ";
        Set<String> set = null;
        set = map.keySet();
        List<String> list = new ArrayList<>(set);
        List<Object> filedlist = new ArrayList<>();
        for (String filed : list) {
            sql += "u." + filed + "=? and ";
            filedlist.add(filed);
        }
        sql = sql.substring(0, sql.length() - 4);
        LOGGER.info(sql + "--------sql语句-------------");
        Query query = entityManager.createQuery(sql);
        for (int i = 0; i < filedlist.size(); i++) {
            query.setParameter(i + 1, map.get(filedlist.get(i)));
        }
        query.setFirstResult((start - 1) * pageNumber);
        query.setMaxResults(pageNumber);
        List<T> listRe = query.getResultList();
        entityManager.close();
        return listRe;
    }

    @Transactional
    @Override
    public List<T> findpages(String tablename, String filed, Object o, int start, int pageNumer) {
        String sql = "from " + tablename + " u WHERE u." + filed + "=?";
        LOGGER.info(sql + "--------page--sql语句-------------");
        List<T> list = new ArrayList<>();
        try {
            Query query = entityManager.createQuery(sql);
            query.setParameter(1, o);
            query.setFirstResult((start - 1) * pageNumer);
            query.setMaxResults(pageNumer);
            list = query.getResultList();
            entityManager.close();
        } catch (Exception e) {
            LOGGER.info("------------分页错误---------------");
        }

        return list;
    }

    @Transactional
    @Override
    public boolean update(T entity) {
        boolean flag = false;
        try {
            entityManager.merge(entity);
            flag = true;
        } catch (Exception e) {
            LOGGER.info("---------------更新出错---------------");
        }
        return flag;
    }

    @Transactional
    @Override
    public Integer updateMoreFiled(String tablename, LinkedHashMap<String, Object> map) {
        String sql = "UPDATE " + tablename + " AS u SET ";
        Set<String> set = null;
        set = map.keySet();
        List<String> list = new ArrayList<>(set);
        for (int i = 0; i < list.size() - 1; i++) {
            if (map.get(list.get(i)).getClass().getTypeName() == "java.lang.String") {
                LOGGER.info("-*****" + map.get(list.get(i)) + "------------" + list.get(i));
                sql += "u." + list.get(i) + "='" + map.get(list.get(i)) + "' , ";
            } else {
                sql += "u." + list.get(i) + "=" + map.get(list.get(i)) + " , ";
            }
        }
        sql = sql.substring(0, sql.length() - 2);
        sql += "where u.id=? ";
        LOGGER.info(sql + "--------sql语句-------------");
        int resurlt = 0;
        try {
            Query query = entityManager.createQuery(sql);
            query.setParameter(1, map.get("id"));
            resurlt = query.executeUpdate();
        } catch (Exception e) {
            LOGGER.info("更新出错-----------------------");
            e.printStackTrace();

        }
        return resurlt;
    }

    @Transactional
    @Override
    public boolean delete(T entity) {
        boolean flag = false;
        try {
            entityManager.remove(entityManager.merge(entity));
            flag = true;
        } catch (Exception e) {
            LOGGER.info("---------------删除出错---------------");
        }
        return flag;
    }

    @Override
    public Object findCount(String tablename, LinkedHashMap<String, Object> map) {
        String sql = "select count(u) from " + tablename + " u WHERE ";
        Set<String> set = null;
        set = map.keySet();
        List<String> list = new ArrayList<>(set);
        List<Object> filedlist = new ArrayList<>();
        for (String filed : list) {
            sql += "u." + filed + "=? and ";
            filedlist.add(filed);
        }
        sql = sql.substring(0, sql.length() - 4);
        LOGGER.info(sql + "--------sql语句-------------");
        Query query = entityManager.createQuery(sql);
        for (int i = 0; i < filedlist.size(); i++) {
            query.setParameter(i + 1, map.get(filedlist.get(i)));
        }
        return query.getSingleResult();
    }
}