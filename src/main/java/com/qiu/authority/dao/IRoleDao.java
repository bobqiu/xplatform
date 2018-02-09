package com.qiu.authority.dao;


import com.qiu.authority.dao.support.IBaseDao;
import com.qiu.authority.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleDao extends IBaseDao<Role, Integer> {

}
