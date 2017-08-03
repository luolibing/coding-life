package cn.design.patterns.responsibilitychain.sample1;

/**
 * Created by luolibing on 2017/7/27.
 */
public class ServeTask extends Task {

    @Override
    public ServiceEvent doExecute(String user, ServiceEntity serviceEntity) {
        return ServiceEvent.SERVE_PASS;
    }
}
