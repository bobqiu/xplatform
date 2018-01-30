package com.qiu.common.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/29 下午11:04
 */
public class JpaBaseDaoImpl<T> {
    protected Class<T> entityClass;
    //@PersistenceContext注解后，entityManager由Spring负责注入
    @PersistenceContext
    protected EntityManager entityManager;

    public JpaBaseDaoImpl() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class<T>) params[0];
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void add(T t) {
        entityManager.persist(t);
    }

    public void update(T t) {
        entityManager.merge(t);
    }

    public T getById(long id) {
        return entityManager.find(entityClass, id);
    }

    public void deleteById(long id) {
        T t = getById(id);
        if (t != null) {
            entityManager.remove(t);
        }
    }

    public void delete(T t) {
        entityManager.remove(t);
    }

    public List<T> getListByPage(int offset, int maxResult) {

        return (List<T>) getEntityManager().createQuery("from " + entityClass.getSimpleName()).setFirstResult(offset).setMaxResults(maxResult).getResultList();
    }

    public List<T> getAll() {
        return (List<T>) getEntityManager().createQuery("from " + entityClass.getSimpleName()).getResultList();
    }

}
