package cn.tim.springboot.transactionmq.entity.jpa;

import cn.tim.springboot.transactionmq.entity.OutBoundOrderAck;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by luolibing on 2017/6/18.
 */
public interface OutBoundOrderAckJpaRepository extends JpaRepository<OutBoundOrderAck, Long> {
}
