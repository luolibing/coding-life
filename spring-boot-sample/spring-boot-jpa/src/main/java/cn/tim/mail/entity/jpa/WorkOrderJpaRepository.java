package cn.tim.mail.entity.jpa;

import cn.tim.mail.entity.Service;
import cn.tim.mail.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by LuoLiBing on 16/3/1.
 */
public interface WorkOrderJpaRepository extends JpaRepository<WorkOrder, Long> {

    public List<WorkOrder> findAllByServiceAndStatusNotNull(Service service);
}
