package com.qiu.common.exception;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/9 下午3:30
 */
public class ParamException extends GlobalException{
    private static final long serialVersionUID = 6021390821349937519L;

    public ParamException(String message) {
        super(message, ResponseCode.PARAM_ERROR_CODE.getCode());
    }
}
