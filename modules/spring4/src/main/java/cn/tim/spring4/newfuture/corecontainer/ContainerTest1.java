package cn.tim.spring4.newfuture.corecontainer;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/5/17.
 */
@Component
@Order(value = Ordered.LOWEST_PRECEDENCE - 1)
public class ContainerTest1 implements Container {
}
