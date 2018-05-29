package com.tim.springboot.jpa;

import cn.tim.mail.Application;
import cn.tim.mail.entity.Order;
import cn.tim.mail.entity.jpa.OrderJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderServiceTest {

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Test
    public void save() {
        Order order = new Order();
//        order.setId(100L);
        order.setCode(100L);
        orderJpaRepository.save(order);

        Order one = orderJpaRepository.findOne(order.getId());
        System.out.println(one);
        Order two = orderJpaRepository.findOne(order.getId());
        System.out.println(two);
    }

    @Test
    public void findOne() {
        // 先查询一级缓存，再二级缓存，二级没有查库，同时更新一级二级缓存
        Order one = orderJpaRepository.findOne(2L);
        System.out.println(one);
        Order two = orderJpaRepository.findOne(2L);
        System.out.println(two);

        orderJpaRepository.delete(2L);
        Order three = orderJpaRepository.findOne(2L);
        System.out.println(three);
    }
}
