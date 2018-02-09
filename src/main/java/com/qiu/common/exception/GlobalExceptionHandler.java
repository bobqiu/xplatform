package com.qiu.common.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/9 下午3:31
 * 使用示例：
 * @RequestMapping("/students")
Object queryStudents() throws GlobalException {
return ResponseData.ok(userService.findAllArea());
}

 @RequestMapping("/students/{name}")
 Object queryStudentByName(@PathVariable String name) throws Exception {
 if(name.equals("1")){
 throw new ParamException("参数错误");
 }
 if(name.equals("2")){
 throw new ServerException("内部错误");
 }
 return ResponseData.ok(userService.findAllArea());
 }
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = GlobalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseData jsonErrorHandler(HttpServletRequest req, GlobalException e) throws Exception {
        //e.printStackTrace();
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        ResponseData r = new ResponseData();
        r.setMessage(e.getMessage());
        r.setCode(e.getCode());
        r.setData(null);
        r.setStatus(false);
        return r;
    }
}
