package com.qiu.user.service.impl;

import com.google.common.collect.Lists;
import com.qiu.common.exception.GlobalException;
import com.qiu.common.exception.ParamException;
import com.qiu.user.dao.CnAreaDao;
import com.qiu.user.dao.UserDao;
import com.qiu.user.model.BirthName;
import com.qiu.user.model.CnArea;
import com.qiu.user.service.UsersService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/31 下午4:26
 */
@Service
public class UsersServiceImpl implements UsersService {
    private static final Logger LOGGER = LogManager.getLogger(UsersServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Autowired
    private CnAreaDao cnAreaDao;


    @Override
    public List<BirthName> findAll() throws GlobalException{
        throw new ParamException("测试参数错误");
       /* Iterable<BirthName> userIterator = userDao.findAll();
        List<BirthName> list = Lists.newArrayList(userIterator);*/
    }

    @Override
    public Page<CnArea> findAllArea() {
       /* Iterable<CnArea> areaIterator = cnAreaDao.findTop10ById(10);
        List<CnArea> list = Lists.newArrayList(areaIterator);*/
        Pageable pageable = new PageRequest(1, 10, Sort.Direction.ASC, "name");
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable1 = new PageRequest(1, 10, sort);
        Page<CnArea> cnAreaDaoAll = cnAreaDao.findAll(pageable1);
        List<CnArea> list = cnAreaDao.findTop100ByCityCode("010");
        for (CnArea cnArea : list) {
            System.out.println(cnArea);
        }
        System.out.println(list.size());
        List<CnArea> prolist = cnAreaDao.findTop100ByParentId(0);
        for (CnArea cnArea : prolist) {
            System.out.println(cnArea);
        }
        System.out.println(prolist.size());
        return cnAreaDaoAll;
    }
}
