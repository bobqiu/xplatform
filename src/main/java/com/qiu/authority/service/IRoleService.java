package com.qiu.authority.service;


import com.qiu.authority.entity.Role;
import com.qiu.authority.service.support.IBaseService;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/1 下午3:14
 */
public interface IRoleService extends IBaseService<Role,Integer> {

	/**
	 * 添加或者修改角色
	 * @param role
	 */
	void saveOrUpdate(Role role);

	/**
	 * 给角色分配资源
	 * @param id 角色ID
	 * @param resourceIds 资源ids
	 */
	void grant(Integer id, String[] resourceIds);

}
