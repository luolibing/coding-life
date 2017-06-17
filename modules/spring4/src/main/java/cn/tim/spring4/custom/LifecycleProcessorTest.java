package cn.tim.spring4.custom;

import org.springframework.context.LifecycleProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/6/17.
 */
@Component
public class LifecycleProcessorTest implements LifecycleProcessor {

    private boolean isRunning = false;

    @Override
    public void onRefresh() {
        System.out.println("onRefresh");
    }

    @Override
    public void onClose() {
        System.out.println("onClose");
    }

    @Override
    public void start() {
        isRunning = true;
        System.out.println("start");
    }

    @Override
    public void stop() {
        isRunning = false;
        System.out.println("stop");
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
