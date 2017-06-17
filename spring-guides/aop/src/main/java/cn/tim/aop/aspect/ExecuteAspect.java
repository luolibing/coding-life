package cn.tim.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/1/11.
 * 切面
 */
@Aspect
@Component
public class ExecuteAspect {

    /**
     * 前置建议
     * @param joinPoint
     */
    @Before(value = "execution(* cn.tim.aop.service.*Service.*(..))")
    public void beforeExecute(JoinPoint joinPoint) {
        System.out.println("beforeExecute !!!");
    }

    /**
     * 后置建议
     * @param joinPoint
     */
    @After(value = "execution(* cn.tim.aop.service.ExecuteService.execute(..))")
    public void afterExecute(JoinPoint joinPoint) {
        System.out.println("afterExecute !!!");
    }

    @Around(value = "execution(* cn.tim.aop.web.*Controller.*(..))")
    public Object handleController(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(joinPoint);
        return joinPoint.proceed();
    }

    /**
     * 环绕建议
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "execution(* cn.tim.aop.service.ExecuteService.execute(..))")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("before");
        final Object result = joinPoint.proceed();
        System.out.println("after");
        return result;
    }

    /**
     * 环绕注解 SPEL spring表达式语言
     * @param joinPoint
     * @return
     */
    @Around("@annotation(org.springframework.stereotype.Service)")
    public Object handleService(ProceedingJoinPoint joinPoint) {
        System.out.println("before service");
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        } finally {
            System.out.println("after service");
        }
    }


    @Around("@annotation(org.springframework.web.bind.annotation.RestController)")
    public Object test(ProceedingJoinPoint joinPoint) {
        System.out.println("before controller");
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        } finally {
            System.out.println("after controller");
        }
    }

}

