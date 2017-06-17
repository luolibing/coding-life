package cn.tim.spring4.custom;

import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/6/7.
 */
@Component
public class LifecycleImpl implements Lifecycle {

    @Override
    public void start() {
        System.out.println("startup!!!");
    }

    @Override
    public void stop() {
        System.out.println("stop!!!");
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
