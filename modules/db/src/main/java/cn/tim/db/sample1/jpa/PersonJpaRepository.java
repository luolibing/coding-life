package cn.tim.db.sample1.jpa;

import cn.tim.db.sample1.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by LuoLiBing on 17/2/21.
 */
public interface PersonJpaRepository extends JpaRepository<Person, Long> {

    @Modifying
    @Query(value = "delete from Person p where p.age=?1")
    void deleteByAge(Integer age);

    @Modifying
    @Query(value = "delete from Person p where p.age=?1")
    void deleteByAgePessimistic(Integer age);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select p from Person p where p.age=?1")
    List<Person> findByAgePessimistic(Integer age);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query(value = "select p from Person p where p.age=?1")
    List<Person> findByAgePessimisticRead(Integer age);

    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    Person findById(Long id);
}
