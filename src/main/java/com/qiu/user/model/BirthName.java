package com.qiu.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/1/31 下午4:14
 */
@Entity
@Table(name = "birthname")
@Data
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class BirthName {

    private int id;
    private String state;
    @Id
    private int year;
    private String name;
    private String gender;
    private  int num;
}
