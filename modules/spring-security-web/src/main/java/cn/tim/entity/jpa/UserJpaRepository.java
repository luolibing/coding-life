package cn.tim.entity.jpa;

import cn.tim.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by LuoLiBing on 15/8/29.
 */
public interface UserJpaRepository extends JpaRepository<User, Long> {

    // Optional JAVA8新特性，允许list为空不会报出空指针异常
    User findByUsername(String username);

}
