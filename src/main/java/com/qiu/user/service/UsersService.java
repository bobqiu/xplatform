package com.qiu.user.service;

import com.qiu.common.exception.GlobalException;
import com.qiu.user.model.BirthName;
import com.qiu.user.model.CnArea;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/31 下午4:23
 */
public interface UsersService {
    public List<BirthName> findAll() throws GlobalException;

    Page<CnArea> findAllArea();
}
