package cn.tim.springboot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * User: luolibing
 * Date: 2017/6/1 14:15
 */
@Aspect
@Component
public class Aspecter {

    private final static Logger logger = LoggerFactory.getLogger(Aspecter.class);

    @Around(value = "execution(* cn.tim.springboot.aspect.*Manage.*(..)))")
    public Object throwing(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            return exceptionHandle(joinPoint, throwable);
        }
    }

    public Object exceptionHandle(JoinPoint joinPoint, Throwable ex) {
        logger.error("aspect exception handle with exception : " + ex);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ThrowingDefaultWrapper wrapper = method.getAnnotation(ThrowingDefaultWrapper.class);

        if(wrapper != null) {
            Class<? extends Defaultable> defaultClazz = wrapper.value();
            try {
                Defaultable defaultable = defaultClazz.newInstance();
                return defaultable.defaultValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException(ex);
    }
}
