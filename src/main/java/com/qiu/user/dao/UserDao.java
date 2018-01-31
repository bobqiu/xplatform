package com.qiu.user.dao;

import com.qiu.user.model.BirthName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/31 下午4:28
 */
@Repository
public interface UserDao extends CrudRepository<BirthName,Long>{
}
