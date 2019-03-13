package cn.tim.springsrc.aop.simple;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.support.AopUtils;

/**
 * Created by luolibing on 2017/6/9.
 */
public class AServiceImpl1 implements AService {

    public void a() {
        System.out.println("execute a");
        // 这个地方的this指向的是目标对象，因此this.b()不会执行事务切面
        this.b();

        // 从AopContext上下文中取出当前代理对象，执行代理对象的b()方法
        ((AService)AopContext.currentProxy()).b();
    }

    public void b() {
        System.out.println("execute b");
    }

    public static class ProxyUtils {

        /***
         * 所有的spring proxy都可以转换为Advised，以方便对Aop进行操作配置
         * @param proxy
         * @param <T>
         * @return
         */
        public static <T> T getProxyTarget(Object proxy) {
            if (!AopUtils.isAopProxy(proxy)) {
                throw new IllegalStateException("Target must be a proxy");
            }
            TargetSource targetSource = ((Advised) proxy).getTargetSource();
            return getTarget(targetSource);
        }

        private static <T> T getTarget(TargetSource targetSource) {
            try {
                return (T) targetSource.getTarget();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
