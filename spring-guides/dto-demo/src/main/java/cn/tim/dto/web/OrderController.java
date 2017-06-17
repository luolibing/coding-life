package cn.tim.dto.web;

import cn.tim.dto.service.OrderService;
import cn.tim.dto.view.OrderView;
import org.jdto.DTOBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LuoLiBing on 16/3/18.
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DTOBinder binder;

    @RequestMapping(value = "/order/{orderId}")
    public OrderView findOrder(@PathVariable Long orderId) {
        return binder.bindFromBusinessObject(OrderView.class, orderService.findOrder(orderId));
    }
}
