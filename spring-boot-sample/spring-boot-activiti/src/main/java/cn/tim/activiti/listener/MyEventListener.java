package cn.tim.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * User: luolibing
 * Date: 2017/6/14 9:45
 */
public class MyEventListener implements ExecutionListener {


    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("MyEventListener received notify = " + execution);
    }
}
