package cn.tim.dto.service;

import cn.tim.dto.entity.Order;
import cn.tim.dto.entity.jpa.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LuoLiBing on 16/3/18.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order findOrder(Long orderId) {
        return orderRepository.findOne(orderId);
    }
}
