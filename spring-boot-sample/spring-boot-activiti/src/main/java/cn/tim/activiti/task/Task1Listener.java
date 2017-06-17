package cn.tim.activiti.task;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.pvm.delegate.TaskListener;

/**
 * User: luolibing
 * Date: 2017/6/13 17:50
 */
public class Task1Listener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("notify task1 " + delegateTask);
    }
}
