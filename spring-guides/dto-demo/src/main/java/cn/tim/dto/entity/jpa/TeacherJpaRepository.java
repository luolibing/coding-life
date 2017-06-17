package cn.tim.dto.entity.jpa;

import cn.tim.dto.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by LuoLiBing on 16/3/17.
 */
public interface TeacherJpaRepository extends JpaRepository<Teacher, Long> {
}
