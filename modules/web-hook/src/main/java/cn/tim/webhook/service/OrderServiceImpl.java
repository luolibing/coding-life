package cn.tim.webhook.service;

import cn.tim.webhook.entity.OrderEntity;
import cn.tim.webhook.form.OrderForm;

/**
 * Created by luolibing on 2017/5/22.
 */
public class OrderServiceImpl implements OrderService {


    @Override
    public OrderEntity create(OrderForm orderForm) {
        return null;
    }

    @Override
    public void pay(Long orderId) {

    }

    @Override
    public void finish() {

    }

    @Override
    public void close() {

    }
}
