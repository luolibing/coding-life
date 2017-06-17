package cn.tim.spring4.newfuture.corecontainer;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/5/17.
 * 1 继承不能继承@Component注解
 * 2 @Order接口,注入的对象的顺序,越小,注入顺序越靠前
 */
@Component
@Order(value = Ordered.LOWEST_PRECEDENCE - 2)
public class ContainerTest2 implements Container {
}
