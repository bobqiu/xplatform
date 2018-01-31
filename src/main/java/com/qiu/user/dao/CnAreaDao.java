package com.qiu.user.dao;

import com.qiu.user.model.CnArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/31 下午5:05
 */
@Repository
public interface CnAreaDao extends JpaRepository<CnArea,Long> {
    Page<CnArea> findAll(Pageable i);
    List<CnArea> findTop100ByCityCode(String code);
    List<CnArea> findTop100ByParentId(int code);
}
