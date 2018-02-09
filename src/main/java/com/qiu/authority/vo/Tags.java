package com.qiu.authority.vo;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/1 下午3:14
 */
public class Tags implements java.io.Serializable{
    private String name;//标签云名称
    private String color;//背景色

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
