package cn.tim.mail.config;

import cn.tim.mail.annotation.CreateNode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.provider.PersistenceProvider;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Method;
import java.util.Properties;

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

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        Properties prop = new Properties();
        prop.setProperty("javax.persistence.sharedCache.mode", "ENABLE_SELECTIVE");
        return Persistence.createEntityManagerFactory("test-pu", prop);
    }
}
