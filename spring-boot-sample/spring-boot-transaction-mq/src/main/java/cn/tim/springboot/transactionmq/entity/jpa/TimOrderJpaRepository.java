package cn.tim.springboot.transactionmq.entity.jpa;

import cn.tim.springboot.transactionmq.entity.TimOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by luolibing on 2017/6/18.
 */
public interface TimOrderJpaRepository extends JpaRepository<TimOrder, Long> {
}
