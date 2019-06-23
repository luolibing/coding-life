package cn.tim.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.history.handler.ActivityInstanceEndHandler;

/**
 * Created by luolibing on 2019/6/16.
 */
public class MyActivityInstanceEndHandler extends ActivityInstanceEndHandler {

    @Override
    public void notify(DelegateExecution execution) {
        super.notify(execution);
        System.out.println("MyActivityInstanceEnd handle " + execution);
    }
}
