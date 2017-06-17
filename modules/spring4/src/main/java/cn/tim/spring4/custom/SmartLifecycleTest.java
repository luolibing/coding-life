package cn.tim.spring4.custom;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/6/17.
 */
@Component
public class SmartLifecycleTest implements SmartLifecycle {

    @Override
    public boolean isAutoStartup() {
        return false;
    }

    @Override
    public void stop(Runnable callback) {
        System.out.println("callback");
    }

    @Override
    public void start() {
        System.out.println("start");
    }

    @Override
    public void stop() {
//        try {
//            Thread.sleep(1000 * 10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("stop");
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return 100;
    }
}
