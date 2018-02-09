package com.qiu.authority.service;

import com.qiu.authority.entity.User;
import com.qiu.authority.service.support.IBaseService;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/1 下午3:14
 */
public interface IUserService extends IBaseService<User, Integer> {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUserName(String username);

    /**
     * 增加或者修改用户
     * @param user
     */
    void saveOrUpdate(User user);

    /**
     * 给用户分配角色
     * @param id 用户ID
     * @param roleIds 角色Ids
     */
    void grant(Integer id, String[] roleIds);

}

