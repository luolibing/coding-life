package cn.tim.mail.service;

import cn.tim.mail.entity.Teacher;
import cn.tim.mail.entity.WorkOrder;
import cn.tim.mail.entity.jpa.ServiceJpaRepository;
import cn.tim.mail.entity.jpa.TeacherJpaRepository;
import cn.tim.mail.entity.jpa.WorkOrderJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LuoLiBing on 16/3/17.
 */
@Service
//@Transactional
public class Test1Service {

    @Autowired
    private TeacherJpaRepository teacherJpaRepository;

    @Autowired
    private WorkOrderJpaRepository workOrderJpaRepository;

    @Autowired
    private
    ServiceJpaRepository serviceJpaRepository;

    public Teacher findTeacher(Long id) {
        Teacher teacher = teacherJpaRepository.findOne(id);
        // 加事务变成持久态
        teacher.setName("李镇洞");
        return teacher;
    }

    public void save(Teacher teacher) {
        final Teacher teacher1 = teacherJpaRepository.save(teacher);
        // 加事务变成持久态
        teacher1.setName("李振强");
    }

    public List<Teacher> findByNameNotNull() {
        return teacherJpaRepository.findAllByNameNotNull();
    }

    public List<WorkOrder> findByStatusNotNull() {
        cn.tim.mail.entity.Service service = serviceJpaRepository.findOne(5L);
        return workOrderJpaRepository.findAllByServiceAndStatusNotNull(service);
    }

    public List<Teacher> findTeacher() {
        return teacherJpaRepository.findByName("罗立兵1111");
    }
}
