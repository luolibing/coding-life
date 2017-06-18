package cn.tim.springboot.transactionmq.service;

import cn.tim.springboot.transactionmq.entity.TimOutboundOrder;
import cn.tim.springboot.transactionmq.mq.Consumer;
import org.springframework.stereotype.Component;

/**
 * Created by luolibing on 2017/6/18.
 */
@Component
public class TimOutboundOrderConsumer implements Consumer<TimOutboundOrder> {

    @Override
    public Class<TimOutboundOrder> supportMessageClazz() {
        return TimOutboundOrder.class;
    }

    @Override
    public void handleMessage(TimOutboundOrder message) {
        System.out.println("收到发货单，照单发货" + message);
    }
}
