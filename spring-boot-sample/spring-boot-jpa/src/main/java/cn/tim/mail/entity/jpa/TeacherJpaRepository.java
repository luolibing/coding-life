package cn.tim.mail.entity.jpa;

import cn.tim.mail.annotation.CreateNode;
import cn.tim.mail.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by LuoLiBing on 16/3/17.
 */
public interface TeacherJpaRepository extends JpaRepository<Teacher, Long> {

    @Query(value = "select t from Teacher t,TeacherHasRole r where t.id=r.teacherId and r.roleId=?1")
    Page<Teacher> findTeacher1(Pageable pageable, Long roleId);

    @CreateNode(tid = "artist")
    List<Teacher> findByName(String name);

    @Query(value = "select t from Teacher t where t.name is not null")
    List<Teacher> findAllByNameNotNull();
}
