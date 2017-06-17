package cn.tim.springsrc.aop.simple;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Created by luolibing on 2017/6/7.
 */
@Aspect
public class AspectTest {

    @Pointcut("execution(* *.test(..))")
    public void test() {

    }

    @Before("test()")
    public void beforeTest() {
        System.out.println("beforeTest");
    }

    @After("test()")
    public void afterTest() {
        System.out.println("afterTest");
    }

    /**
     * 先命中了around, 然后才是before。
     * around优先级会比before和after高一些
     * @param joinPoint
     * @return
     */
    @Around("test()")
    public Object aroundTest(ProceedingJoinPoint joinPoint) {
        System.out.println("before1");
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("after1");
        return result;
    }

    @Around("execution(* *.*(..))")
    public Object aroundAService(ProceedingJoinPoint joinPoint) {
        System.out.println("before1");
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("after1");
        return result;
    }
}
