package cn.tim.activiti.myprocess;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * User: luolibing
 * Date: 2017/6/19 11:21
 */
public class EndEventListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String yearMonth = (String) execution.getVariable("yearMonth");
        System.out.println("报告" + yearMonth + "通过");
    }
}
