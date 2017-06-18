package cn.tim.springboot.transactionmq.task;

import cn.tim.springboot.transactionmq.service.TimOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by luolibing on 2017/6/18.
 */
@Component
public class OrderCreateTask {

    @Autowired
    private TimOrderService timOrderService;

    @Scheduled(cron = "0/10 * * * * *")
    public void execute() {
        try {
            timOrderService.createOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
