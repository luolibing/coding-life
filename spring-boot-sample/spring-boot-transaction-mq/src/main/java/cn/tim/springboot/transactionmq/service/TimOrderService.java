package cn.tim.springboot.transactionmq.service;

import cn.tim.springboot.transactionmq.entity.TimOrder;
import cn.tim.springboot.transactionmq.entity.TimOutboundOrder;
import cn.tim.springboot.transactionmq.entity.jpa.TimOrderJpaRepository;
import cn.tim.springboot.transactionmq.entity.jpa.TimOutboundOrderJpaRepository;
import cn.tim.springboot.transactionmq.mq.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.transaction.Transactional;
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

    /**
     * 这个地方，如果发送mq失败或者最后更新状态失败都没关系，会有定时器来补偿，同时mq那如果消费失败，只要不正常返回ack，都能够重试消息，需要注意的是消息接收方需要保证幂等去重.
     * @throws Exception
     */
    @Transactional
    public void createOrder1() throws Exception {
        TimOrder timOrder = createTimOrder();
        timOrderJpaRepository.save(timOrder);
        TimOutboundOrder outboundOrder = createOutboundOrder(timOrder.getId());

        // 先保存
        timOutboundOrderJpaRepository.save(outboundOrder);

        // 再发mq
        mqProducer.send("spring-boot", outboundOrder);

        // 最后更新状态
        outboundOrder.setSent(1);
        timOutboundOrderJpaRepository.save(outboundOrder);
    }


    @Autowired
    private PlatformTransactionManager txManager;


    /**
     * 编程式事务处理
     * @throws Exception
     */
    public void createOrder() throws Exception {
        TransactionTemplate transactionTemplate = getDefaultTransactionTemplate(txManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult(){

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                TimOrder timOrder = createTimOrder();
                timOrderJpaRepository.save(timOrder);
                TimOutboundOrder outboundOrder = createOutboundOrder(timOrder.getId());

                // 先保存
                timOutboundOrderJpaRepository.save(outboundOrder);

                // 再发mq
                try {
                    mqProducer.send("spring-boot", outboundOrder);

                    // 最后更新状态
                    outboundOrder.setSent(1);
                    timOutboundOrderJpaRepository.save(outboundOrder);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public static TransactionTemplate getDefaultTransactionTemplate(
            PlatformTransactionManager txManager) {
        return getTransactionTemplate(txManager,
                TransactionDefinition.PROPAGATION_REQUIRED,
                TransactionDefinition.ISOLATION_READ_COMMITTED);
    }

    public static TransactionTemplate getTransactionTemplate(
            PlatformTransactionManager txManager, int propagationBehavior,
            int isolationLevel) {

        TransactionTemplate transactionTemplate = new TransactionTemplate(
                txManager);
        transactionTemplate.setPropagationBehavior(propagationBehavior);
        transactionTemplate.setIsolationLevel(isolationLevel);
        return transactionTemplate;
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