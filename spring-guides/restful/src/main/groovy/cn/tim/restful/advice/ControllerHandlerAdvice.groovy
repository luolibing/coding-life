package cn.tim.restful.advice
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.springframework.web.bind.annotation.ControllerAdvice
/**
 * Created by LuoLiBing on 16/3/16.
 */
@ControllerAdvice
class ControllerHandlerAdvice {

    @After(value = "execution(* cn.tim.aop.service.ExecuteService.execute(..))")
    public Object afterExecute(JoinPoint joinpoint) {
        joinpoint
    }
}
