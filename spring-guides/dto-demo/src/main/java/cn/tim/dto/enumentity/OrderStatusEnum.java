package cn.tim.dto.enumentity;

import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/3/18.
 */
@Component
public class OrderStatusEnum extends BaseEnum {

    private final static String name = "orderStatus";

    public OrderStatusEnum() {
        put(10, "创建");
        put(11, "已支付");
        put(20, "已完成");
        put(30, "已取消");
        put(40, "已退单");
        EnumProvider.addEnum(name, this);
    }
}
