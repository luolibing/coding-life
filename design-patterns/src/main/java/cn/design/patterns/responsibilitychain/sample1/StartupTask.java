package cn.design.patterns.responsibilitychain.sample1;

/**
 * Created by luolibing on 2017/7/27.
 */
public class StartupTask extends Task {

    @Override
    public ServiceEvent doExecute(String user, ServiceEntity serviceEntity) {
        System.out.println("启动任务");
        return ServiceEvent.STARTUP;
    }
}
