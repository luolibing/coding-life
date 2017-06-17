package cn.tim.restful.advice

import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
/**
 * Created by LuoLiBing on 16/3/16.
 */
class ResuleControllerAdvice implements HandlerInterceptor {

    @Override
    boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        httpServletRequest.setAttribute("start_time", System.currentTimeMillis())
        return true
    }

    @Override
    void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        println modelAndView
        println o.toString()
    }

    @Override
    void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        println o
    }
}
