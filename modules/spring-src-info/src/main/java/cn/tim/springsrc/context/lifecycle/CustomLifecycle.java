package cn.tim.springsrc.context.lifecycle;

import org.springframework.context.LifecycleProcessor;

/**
 * Created by luolibing on 2017/6/7.
 */
public class CustomLifecycle implements LifecycleProcessor {
    @Override
    public void onRefresh() {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
