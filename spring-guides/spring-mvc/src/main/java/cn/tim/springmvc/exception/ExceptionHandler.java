package cn.tim.springmvc.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 方式与spring boot中的@ExceptionHandler类似
 * Created by luolibing on 2017/7/4.
 */
public class ExceptionHandler implements HandlerExceptionResolver {


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        request.setAttribute("exception", ex.toString());
        request.setAttribute("exceptionStack", ex);
        if(ex instanceof NullPointerException) {

        } else if(ex instanceof IOException) {

        }
        return new ModelAndView("error/exception");
    }
}
