package cn.tim.mail.config;

import cn.tim.mail.annotation.CreateNode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.reflect.Method;

/**
 * User: luolibing
 * Date: 2018/3/20 14:28
 */
@EnableAspectJAutoProxy
@Configuration
@Aspect
public class JpaConfiguration {

    @Around("execution(public * org.springframework.data.jpa.repository.JpaRepository+.*(..))")
    // @Around("@annotation(Repository)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CreateNode createNode = method.getAnnotation(CreateNode.class);
        if(createNode != null) {
            Object[] args = joinPoint.getArgs();
            // do your business

        }
        return joinPoint.proceed();
    }
}
