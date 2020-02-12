package cn.tim.interceptor;

import cn.tim.interceptor.annotation.GLock;
import org.apache.commons.text.StringEscapeUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: luolibing
 * Date: 2017/8/8 9:59
 */
@Aspect
@Component
public class GLockInterceptor {

    private Map<String, ReentrantLock> conditionMap = new ConcurrentHashMap<>();

    @Around(value = "@annotation(cn.tim.interceptor.annotation.GLock)")
    public Object proxy(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        GLock glock = method.getAnnotation(GLock.class);
        if(glock != null) {
            String key = String.valueOf(parseSpelKey(
                    methodSignature.getParameterNames(), joinPoint.getArgs(), glock.key()));

            ReentrantLock lock = conditionMap.computeIfAbsent(key, k -> new ReentrantLock());
            lock.lock();
            try {
                return joinPoint.proceed();
            } finally {
                lock.unlock();
            }
        } else {
            return joinPoint.proceed();
        }
    }


    private Object parseSpelKey(String[] parameterNames, Object[] args, String key) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context);
    }

    public static void main(String[] args) {
        String sku = StringEscapeUtils.escapeHtml4("A1266021&B06XF6DR53");
        System.out.println(sku);
    }
}
