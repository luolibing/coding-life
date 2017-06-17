package cn.tim.aop.web;

import org.apache.catalina.Session;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by LuoLiBing on 16/1/11.
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String allException(Exception e) {
        return e.getMessage();
    }

    // 每次都会进行判断
    @ModelAttribute("currentUser")
    public String getBaseUser(Session session) {
        return (session == null) ? null: session.getId();
    }
}
