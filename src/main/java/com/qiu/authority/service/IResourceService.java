package com.qiu.authority.service;


import com.qiu.authority.entity.Resource;
import com.qiu.authority.service.support.IBaseService;
import com.qiu.authority.vo.ZtreeView;

import java.util.List;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/1 下午3:14
 */
public interface IResourceService extends IBaseService<Resource, Integer> {

	/**
	 * 获取角色的权限树
	 * @param roleId
	 * @return
	 */
	List<ZtreeView> tree(int roleId);

	/**
	 * 修改或者新增资源
	 * @param resource
	 */
	void saveOrUpdate(Resource resource);

}
