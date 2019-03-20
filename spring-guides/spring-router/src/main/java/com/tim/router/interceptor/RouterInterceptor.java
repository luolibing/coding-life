package com.tim.router.interceptor;

import com.tim.router.domain.Router;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Aspect
@Order
public class RouterInterceptor implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Around(value = "@annotation(com.tim.router.domain.Router)")
    public Object proxy(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Method method = methodSignature.getMethod();
        Collection<?> beanList = getImplOrderBeanList(joinPoint, method);
        System.out.println(beanList);
        if(CollectionUtils.isEmpty(beanList)) {
            return joinPoint.proceed();
        } else {
            Object proxy = beanList.iterator().next();
            return method.invoke(((Advised)proxy).getTargetSource().getTarget(), joinPoint.getArgs());
        }
    }

    private Collection<?> getImplOrderBeanList(ProceedingJoinPoint joinPoint, Method method) {
        Class<?>[] interfaces = joinPoint.getTarget().getClass().getInterfaces();
        if(interfaces == null || interfaces.length == 0) {
            return Collections.emptyList();
        }

        List<Class<?>> clazzList = Stream.of(interfaces)
                .filter(inter -> Arrays.asList(inter.getMethods()).contains(method))
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(clazzList)) {
            return Collections.emptyList();
        }

        Map<String, ?> beanMap = applicationContext.getBeansOfType(clazzList.get(0));
        return beanMap.values()
                .stream()
                .sorted((o1, o2) -> {
                    Method method1 = findMethod(o1, method);
                    Method method2 = findMethod(o2, method);
                    int order1 = getOrderByRoute(method1);
                    int order2 = getOrderByRoute(method2);
                    return Integer.compare(order1, order2);
                })
                .collect(Collectors.toList());
    }

    private Method findMethod(Object o1, Method method) {
        try {
            return ((Advised)o1).getTargetClass().getMethod(method.getName(), method.getParameterTypes());
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private int getOrderByRoute(Method method) {
        int order = -1;
        if(method != null) {
            Router router = method.getAnnotation(Router.class);
            if(router != null) {
                order = router.order();
            }
        }

        return order;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
