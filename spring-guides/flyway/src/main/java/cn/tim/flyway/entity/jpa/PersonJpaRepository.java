package cn.tim.flyway.entity.jpa;

import cn.tim.flyway.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by LuoLiBing on 16/1/12.
 */
public interface PersonJpaRepository extends JpaRepository<Person, Long> {
}
