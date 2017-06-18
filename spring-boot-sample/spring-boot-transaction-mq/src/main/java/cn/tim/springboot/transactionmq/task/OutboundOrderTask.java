package cn.tim.springboot.transactionmq.task;

import cn.tim.springboot.transactionmq.entity.TimOutboundOrder;
import cn.tim.springboot.transactionmq.entity.jpa.TimOutboundOrderJpaRepository;
import cn.tim.springboot.transactionmq.mq.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by luolibing on 2017/6/18.
 */
@Component
public class OutboundOrderTask {

    @Autowired
    private TimOutboundOrderJpaRepository timOutboundOrderJpaRepository;

    @Autowired
    private MqProducer mqProducer;

    @Scheduled(cron = "0/10 * * * * *")
    public void execute() {
        List<TimOutboundOrder> timOutboundOrderList = timOutboundOrderJpaRepository.findBySent(0);
        if(CollectionUtils.isEmpty(timOutboundOrderList)) {
            return;
        }

        for(TimOutboundOrder outboundOrder : timOutboundOrderList) {
            // 再发mq
            try {
                mqProducer.send("spring-boot", outboundOrder);
                outboundOrder.setSent(1);
                timOutboundOrderJpaRepository.save(outboundOrder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
