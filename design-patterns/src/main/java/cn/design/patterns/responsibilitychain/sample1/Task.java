package cn.design.patterns.responsibilitychain.sample1;

/**
 * Created by luolibing on 2017/7/27.
 */
public abstract class Task {

    /**
     * 下一个节点
     */
    protected Task nextHandler = null;

    public Task getNextHandler() {
        return nextHandler;
    }

    public void setNextHandler(Task nextHandler) {
        this.nextHandler = nextHandler;
    }

    public ServiceEvent execute(String user, ServiceEntity serviceEntity) {
        ServiceEvent serviceEvent = doExecute(user, serviceEntity);

        // 设配下一个serviceEvent处理的handler
        nextHandler = getHandleTask(serviceEvent);


        if(nextHandler != null) {
            saveTask(nextHandler, serviceEntity);
        }

        return serviceEvent;
    }

    public abstract ServiceEvent doExecute(String user, ServiceEntity serviceEntity);

    private void saveTask(Task nextHandler, ServiceEntity serviceEntity) {

    }

    private Task getHandleTask(ServiceEvent serviceEvent) {
        // event对应task
        return null;
    }
}
