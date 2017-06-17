package cn.tim.webhook.entity;

/**
 * Created by luolibing on 2017/5/22.
 */
public interface OrderState {

    // 创建订单
    void createOrder();

    // 支付
    void paidOrder();

    // 订单完成
    void finishOrder();

    // 关闭订单
    void closedOrder();
}
