package cn.tim.springboot.transactionmq.entity.jpa;

import cn.tim.springboot.transactionmq.entity.TimOutboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by luolibing on 2017/6/18.
 */
public interface TimOutboundOrderJpaRepository extends JpaRepository<TimOutboundOrder, Long> {

    List<TimOutboundOrder> findBySent(Integer sent);
}
