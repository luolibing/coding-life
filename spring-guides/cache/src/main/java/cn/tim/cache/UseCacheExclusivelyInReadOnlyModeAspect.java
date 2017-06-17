package cn.tim.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

/**
 * Created by LuoLiBing on 16/1/4.
 */
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class UseCacheExclusivelyInReadOnlyModeAspect {

    private final boolean readOnly;

    public UseCacheExclusivelyInReadOnlyModeAspect(final boolean readOnly) {
        this.readOnly = readOnly;
    }

    protected boolean isReadOnly() {
        return readOnly;
    }

    protected boolean isNotReadOnly() {
        return !isReadOnly();
    }

    @Around("@annotation(org.springframework.cache.annotation.Cacheable)")
    public Object handleReadOnlyMode(ProceedingJoinPoint joinPoint) throws Throwable {
        System.err.printf("**Around Advice Invoked (read-only = %1$s)**%n", isReadOnly());
        if (isNotReadOnly()) {
            System.err.printf("**Proceeding with Joint Point Execution - %1$s(%2$s)**%n",
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
            return joinPoint.proceed();
        }

        return null;
    }
}
