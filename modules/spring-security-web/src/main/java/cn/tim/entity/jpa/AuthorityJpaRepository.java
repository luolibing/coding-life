package cn.tim.entity.jpa;

import cn.tim.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


/**
 * Created by LuoLiBing on 15/9/1.
 */
@Component
public interface AuthorityJpaRepository extends JpaRepository<Authority, Long> {

}
