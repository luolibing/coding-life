package cn.tim.springboot.returnvalue.handler.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * User: luolibing
 * Date: 2017/8/31 18:38
 */
@ControllerAdvice(annotations = org.springframework.stereotype.Controller.class)
public class ControllerExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    public String defaultExceptionHandle(Exception e) {
        return "404";
    }
}
