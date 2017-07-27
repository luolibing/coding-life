package cn.design.patterns.responsibilitychain.sample1;

/**
 * Created by luolibing on 2017/7/27.
 */
public class TaskChain {

    public void execute(ServiceEntity serviceEntity) {
        StartupTask startupTask = new StartupTask();
        startupTask.execute("luolibing", serviceEntity);
    }
}
