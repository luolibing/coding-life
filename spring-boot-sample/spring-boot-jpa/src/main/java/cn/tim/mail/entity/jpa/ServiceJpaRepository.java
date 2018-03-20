package cn.tim.mail.entity.jpa;

import cn.tim.mail.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by LuoLiBing on 16/2/29.
 */
public interface ServiceJpaRepository extends JpaRepository<Service, Long>{

    Service save(Service service);
}
