package cn.tim.activiti.myprocess;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * User: luolibing
 * Date: 2017/6/19 11:04
 */
public class StartEventListener implements ExecutionListener {

    public void startNotify(String month) {
        System.out.println("开始" + month + "报告");
    }

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String yearMonth = (String) execution.getVariable("yearMonth");
        System.out.println("开始" + yearMonth + "报告");
    }
}
