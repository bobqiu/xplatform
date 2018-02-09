package com.qiu.common.exception;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/9 下午3:31
 */
public class ResponseData {
    private Boolean status = true;
    private int code = 200;
    private String message;
    private Object data;

    public static ResponseData ok(Object data) {
        return new ResponseData(data);
    }
    public ResponseData(Object data) {
        super();
        this.data = data;
    }

    public ResponseData() {
        super();
    }

    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

}
