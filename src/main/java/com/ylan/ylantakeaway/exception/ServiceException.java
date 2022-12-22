package com.ylan.ylantakeaway.exception;

/**
 * 自定义业务异常
 *
 * @author by ylan
 * @date 2022-12-19 20:58
 */

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
