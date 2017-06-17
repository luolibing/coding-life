package cn.tim.envers.entity.jpa;

import cn.tim.envers.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

/**
 * Created by LuoLiBing on 15/12/19.
 * RevisionRepository<Person, Long, Integer>,
 */
public interface PersonJpaRepository extends RevisionRepository<Person, Long, Integer>,JpaRepository<Person, Long> {
}
