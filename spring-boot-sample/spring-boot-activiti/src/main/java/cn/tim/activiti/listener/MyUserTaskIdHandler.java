package cn.tim.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.history.handler.UserTaskIdHandler;

/**
 * Created by luolibing on 2019/6/16.
 */
public class MyUserTaskIdHandler extends UserTaskIdHandler {

    @Override
    public void notify(DelegateTask task) {
        super.notify(task);
        System.out.println("MyActivityInstanceEnd handle " + task);
    }
}
