package cn.design.patterns.responsibilitychain.sample1;

/**
 * Created by luolibing on 2017/7/27.
 */
public class ManagerTask extends Task {
    @Override
    public ServiceEvent doExecute(String user, ServiceEntity serviceEntity) {
        return ServiceEvent.MANAGER_PASS;
    }
}
