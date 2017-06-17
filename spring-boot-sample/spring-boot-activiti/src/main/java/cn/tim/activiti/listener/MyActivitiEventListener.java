package cn.tim.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

/**
 * User: luolibing
 * Date: 2017/6/14 10:15
 */
public class MyActivitiEventListener implements ActivitiEventListener {

    @Override
    public void onEvent(ActivitiEvent event) {
        System.out.println("received activitiEvent = " + event);
    }

    @Override
    public boolean isFailOnException() {
        // ignore exception
        return false;
    }
}
