package cn.tim.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by TIM on 2015/8/30.
 * 所有异常处理
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String allException(UsernameNotFoundException e) {
        return e.getMessage();
    }
}
