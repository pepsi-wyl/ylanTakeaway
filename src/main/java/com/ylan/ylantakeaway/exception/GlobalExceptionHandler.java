package com.ylan.ylantakeaway.exception;

import com.ylan.ylantakeaway.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author by ylan
 * @date 2022-12-17 18:18
 * 全局异常处理
 */

@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class}) // Controller通知
public class GlobalExceptionHandler {

    /**
     * 主键存在异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        String message = exception.getMessage();
        log.error(message);
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String error = split[2] + "已经存在";
            return R.error(error);
        }
        return R.error("未知错误");
    }

    /**
     * Service异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public R<String> exceptionHandler(ServiceException exception) {
        String message = exception.getMessage();
        log.error(message);
        return R.error(message);
    }

}
