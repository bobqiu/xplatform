package com.qiu.authority.dao;

import com.qiu.authority.dao.support.IBaseDao;
import com.qiu.authority.entity.User;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserDao extends IBaseDao<User, Integer> {

	User findByUserName(String username);

}
