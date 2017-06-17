package cn.tim.webhook.form;

import lombok.Data;

/**
 * Created by luolibing on 2017/5/22.
 */
@Data
public class OrderForm {

    private Long skuId;

    private Long userId;

    private Long count;

}
