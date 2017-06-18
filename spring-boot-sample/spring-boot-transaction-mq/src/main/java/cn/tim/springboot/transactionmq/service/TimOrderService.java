package cn.tim.springboot.transactionmq.service;

import cn.tim.springboot.transactionmq.entity.TimOrder;
import cn.tim.springboot.transactionmq.entity.TimOutboundOrder;
import cn.tim.springboot.transactionmq.entity.jpa.TimOrderJpaRepository;
import cn.tim.springboot.transactionmq.entity.jpa.TimOutboundOrderJpaRepository;
import cn.tim.springboot.transactionmq.mq.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by luolibing on 2017/6/18.
 */
@Service
public class TimOrderService {

    @Autowired
    private TimOrderJpaRepository timOrderJpaRepository;

    @Autowired
    private TimOutboundOrderJpaRepository timOutboundOrderJpaRepository;

    @Autowired
    private MqProducer mqProducer;

    public void createOrder() throws Exception {
        TimOrder timOrder = createTimOrder();
        timOrderJpaRepository.save(timOrder);
        TimOutboundOrder outboundOrder = createOutboundOrder(timOrder.getId());

        // 先保存
        timOutboundOrderJpaRepository.save(outboundOrder);
        // 再发mq
        mqProducer.send("spring-boot", outboundOrder);

        outboundOrder.setSent(1);
        timOutboundOrderJpaRepository.save(outboundOrder);
    }

    private TimOrder createTimOrder() {
        TimOrder timOrder = new TimOrder();
        timOrder.setOrderCode(System.currentTimeMillis());
        timOrder.setProductId(1001L);
        timOrder.setProductName("macbook touchbar 2013");
        timOrder.setCount(1);
        timOrder.setPrice(1300000L);
        timOrder.setAmount(1300000L);
        timOrder.setCreated(new Date());
        return timOrder;
    }

    private TimOutboundOrder createOutboundOrder(Long orderId) {
        TimOutboundOrder timOutboundOrder = new TimOutboundOrder();
        timOutboundOrder.setProductId(1001L);
        timOutboundOrder.setCount(1);
        timOutboundOrder.setOrderId(orderId);
        timOutboundOrder.setCreated(new Date());
        timOutboundOrder.setSent(0);
        return timOutboundOrder;
    }
}