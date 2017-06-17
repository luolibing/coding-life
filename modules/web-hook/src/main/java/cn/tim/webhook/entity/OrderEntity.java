package cn.tim.webhook.entity;

import cn.tim.webhook.event.OrderClosedState;
import cn.tim.webhook.event.OrderCreatedState;
import cn.tim.webhook.event.OrderFinishedState;
import cn.tim.webhook.event.OrderPaidState;
import lombok.Data;

/**
 * Created by luolibing on 2017/5/22.
 */
@Data
public class OrderEntity {

    private String id;

    private String name;

    private Long productId;

    private Long amount;

    private Integer status;

    private OrderState state;


    private OrderState created = new OrderCreatedState();

    private OrderState paid = new OrderPaidState();

    private OrderState finish = new OrderFinishedState();

    private OrderState closed = new OrderClosedState();

    public OrderEntity() {
        state = new OrderCreatedState();
        state.createOrder();
    }

}
