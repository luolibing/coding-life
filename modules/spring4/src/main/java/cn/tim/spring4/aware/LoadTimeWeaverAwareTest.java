package cn.tim.spring4.aware;

import org.springframework.context.weaving.LoadTimeWeaverAware;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/6/17.
 * 定义在加载时间处理类定义的weaver
 */
@Component
public class LoadTimeWeaverAwareTest implements LoadTimeWeaverAware {

    @Override
    public void setLoadTimeWeaver(LoadTimeWeaver loadTimeWeaver) {
        System.out.println(loadTimeWeaver);
    }
}
