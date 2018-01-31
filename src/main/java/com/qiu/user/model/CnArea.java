package com.qiu.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/31 下午5:00
 */
@Entity
@Table(name = "cnarea_2016")
@Data
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class CnArea {
    @Id
    private int id;
    private int parentId;
    private int level;
    private BigInteger areaCode;
    private int zipCode;
    private String cityCode;
    private String name;
    private String shortName;
    private String mergerName;
    private String pinyin;
    private double lng;
    private double lat;
}
