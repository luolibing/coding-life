package cn.tim.springboot.transactionmq.mq;

import cn.tim.springboot.transactionmq.entity.OutBoundOrderAck;
import cn.tim.springboot.transactionmq.entity.TimOutboundOrder;
import cn.tim.springboot.transactionmq.entity.jpa.OutBoundOrderAckJpaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by luolibing on 2017/6/18.
 */
@Component
public class OutBoundOrderConsumer {

    @Autowired
    private OutBoundOrderAckJpaRepository outBoundOrderAckJpaRepository;

    @RabbitListener(queues = "spring-boot")
    public void handleMessage(TimOutboundOrder message) {
        System.out.println("handleMessage message = " + message);
        // 出库单id
        Long outBoundId = message.getId();
        OutBoundOrderAck orderAck = new OutBoundOrderAck();
        orderAck.setCreated(new Date());
        orderAck.setOutBoundOrderId(outBoundId);
        outBoundOrderAckJpaRepository.save(orderAck);
    }
}
