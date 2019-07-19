package cn.tim.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;

/**
 * User: luolibing
 * Date: 2017/6/14 10:15
 */
public class MyActivitiEventListener implements ActivitiEventListener {

    @Override
    public void onEvent(ActivitiEvent event) {
        ActivitiEventType type = event.getType();
        System.out.println("received activitiEvent type= " + type + " event = " + event);
    }

    @Override
    public boolean isFailOnException() {
        // ignore exception
        return true;
    }
}
