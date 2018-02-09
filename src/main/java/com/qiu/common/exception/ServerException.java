package com.qiu.common.exception;

/**
 * Describe:
 * Created by: bobqiu
 * Date: 2018/2/9 下午3:28
 */
public class ServerException extends GlobalException{
        private static final long serialVersionUID = -1355046108056594333L;

        public ServerException(String message) {
            super(message, ResponseCode.SERVER_ERROR_CODE.getCode());
        }
}
