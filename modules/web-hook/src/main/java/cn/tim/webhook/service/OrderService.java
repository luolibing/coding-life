package cn.tim.webhook.service;

import cn.tim.webhook.entity.OrderEntity;
import cn.tim.webhook.form.OrderForm;

/**
 * Created by luolibing on 2017/5/22.
 */
public interface OrderService {

    OrderEntity create(OrderForm orderForm);

    void pay(Long orderId);

    void finish();

    void close();
}
